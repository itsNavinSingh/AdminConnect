package handler

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/database"
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
	exists, err = database.IsUserExists(m.App.DataBase, regReq.Email)
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
	if !database.StoreToTemp(m.App.DataBase, &regReq, hashedPassword) {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	err = m.SendOTP(regReq.Email)
	if err != nil {
		status.Message = err.Error()
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

	var storedOTP int
	var generatedAt time.Time
	storedOTP, generatedAt, err = database.GetOTP(m.App.DataBase, otpReq.Email)
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
	registrationData, err = database.GetRegistration(m.App.DataBase, otpReq.Email)
	if err != nil {
		status.Message = "Could not complete Registration"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	if !database.StoreToUser(m.App.DataBase, &registrationData) {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	if !database.DeleteFromTemp(m.App.DataBase, registrationData.Email) {
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
	if !database.DeleteOTP(m.App.DataBase, registrationData.Email) {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	status.Success = true
	out, _ := json.Marshal(status)
	w.Write(out)
}
