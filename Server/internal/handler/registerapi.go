package handler

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/model"
	"golang.org/x/crypto/bcrypt"
)

func (m *Repository) ApiRegister(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var regReq model.Register
	var status model.ApiStatus
	status.Success = false
	err := json.NewDecoder(r.Body).Decode(&regReq)
	if err != nil {
		status.Message = "Invalid Input"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	var exists bool
	err = m.App.DataBase.QueryRow("SELECT EXISTS(SELECT 1 FROM users WHERE email=$1)", regReq.Email).Scan(&exists)
	if err != nil || exists {
		status.Message = "Email is already Exist"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(regReq.Password), bcrypt.DefaultCost)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	_, err = m.App.DataBase.Exec(
		"INSERT INTO pending_users (email, password, phone, course, course_year, croll, uroll, name) VALUES ($1, $2, $3, $4, $5, $6, $7, $8) ON CONFLICT (email) DO UPDATE SET password = $2, phone = $3, course = $4, course_year = $5, croll = $6, uroll = $7, name = $8",
		regReq.Email, hashedPassword, regReq.Phone, regReq.Course, regReq.CourseYear, regReq.CRoll, regReq.URoll, regReq.Name)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	otp := GenerateOTP()
	_, err = m.App.DataBase.Exec(
		"INSERT INTO user_otp (email, otp, generated_at) VALUES ($1, $2, NOW()) ON CONFLICT (email) DO UPDATE SET otp = $2 , generated_at = NOW()",
		regReq.Email, otp)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}

	err = SendOTP(m.App.Semail, m.App.Spassword, regReq.Email, otp)
	if err != nil {
		status.Message = "Could Not Send OTP"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}

	status.Success = true
	status.Message = "OTP is sent to your email"
	out, _ := json.Marshal(status)
	w.Write(out)
}

func (m *Repository) ApiVerifyOTP(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var otpReq model.OTPVerify
	var status model.ApiStatus
	status.Success = false
	err := json.NewDecoder(r.Body).Decode(&otpReq)
	if err != nil {
		status.Message = "Invalid Request"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}

	var storedOTP string
	var generatedAt time.Time
	err = m.App.DataBase.QueryRow("SELECT otp, generated_at FROM users_otp WHERE email=$1", otpReq.Email).Scan(&storedOTP, &generatedAt)
	if err != nil {
		status.Message = "User not found"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}

	if time.Since(generatedAt) > 15*time.Minute {
		status.Message = "OTP Expired"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	if storedOTP != otpReq.OTP {
		status.Message = "Invalid OTP"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	var registrationData model.Register
	registrationData.Email = otpReq.Email
	err = m.App.DataBase.QueryRow(
		"SELECT password, phone, course, course_year, croll, uroll, name FROM pending_users WHERE email=$1",
		otpReq.Email).Scan(
		&registrationData.Password,
		&registrationData.Phone,
		&registrationData.Course,
		&registrationData.CourseYear,
		&registrationData.CRoll,
		&registrationData.URoll,
		&registrationData.Name)
	if err != nil {
		status.Message = "Could not complete Registration"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	tx, err := m.App.DataBase.Begin()
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	defer tx.Rollback()
	_, err = tx.Exec("INSERT INTO users (email, password, phone, course, course_year, croll, uroll, name) VALUES ($1, $2, $3, $4, $5, $6, $7, $8)",
		registrationData.Email, registrationData.Password, registrationData.Phone, registrationData.Course, registrationData.CourseYear, registrationData.CRoll, registrationData.URoll, registrationData.Name)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	_, err = tx.Exec("DELETE FROM pending_users WHERE  email=$1", registrationData.Email)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	status.Message, err = CreateJWTToken(registrationData.Email, m.App.JWTKey)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	_, err = tx.Exec("DELETE FROM users_otp WHERE email=$1", registrationData.Email)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	err = tx.Commit()
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	status.Success = true
	out, _ := json.Marshal(status)
	w.Write(out)
}
