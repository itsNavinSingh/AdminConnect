package database

import (
	"database/sql"
	"time"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/model"
)
// user
func StoreToUser(db *sql.DB, registrationData *model.Register) bool {
	_, err := db.Exec("INSERT INTO users (email, password, phone, course, course_year, croll, uroll, name) VALUES ($1, $2, $3, $4, $5, $6, $7, $8)",
		registrationData.Email, registrationData.Password, registrationData.Phone, registrationData.Course, registrationData.CourseYear, registrationData.CRoll, registrationData.URoll, registrationData.Name)
	return err == nil
}
func IsUserExists(db *sql.DB, email string) (bool, error){
	var exists bool
	err := db.QueryRow("SELECT EXISTS(SELECT 1 FROM users WHERE email=$1)", email).Scan(&exists)
	return exists, err
}
func GetPassword(db *sql.DB, email string) (string, error){
	var hpass string
	err := db.QueryRow("SELECT password FROM users WHERE email=$1", email).Scan(&hpass)
	return hpass, err
}
// pending
func StoreToTemp(db *sql.DB, regReq *model.Register, hashedPassword []byte) bool {
	_, err := db.Exec(
		"INSERT INTO pending_users (email, password, phone, course, course_year, croll, uroll, name) VALUES ($1, $2, $3, $4, $5, $6, $7, $8) ON CONFLICT (email) DO UPDATE SET password = $2, phone = $3, course = $4, course_year = $5, croll = $6, uroll = $7, name = $8",
		regReq.Email, hashedPassword, regReq.Phone, regReq.Course, regReq.CourseYear, regReq.CRoll, regReq.URoll, regReq.Name)
	return err==nil
}
func GetRegistration(db *sql.DB, email string) (model.Register, error) {
	var registrationData model.Register
	registrationData.Email = email
	err := db.QueryRow(
		"SELECT password, phone, course, course_year, croll, uroll, name FROM pending_users WHERE email=$1",
		email).Scan(
		&registrationData.Password,
		&registrationData.Phone,
		&registrationData.Course,
		&registrationData.CourseYear,
		&registrationData.CRoll,
		&registrationData.URoll,
		&registrationData.Name)
	return registrationData, err
}
func DeleteFromTemp(db *sql.DB, email string) bool {
	_, err := db.Exec("DELETE FROM pending_users WHERE  email=$1", email)
	return err == nil
	
}
// otp
func SendOTP(db *sql.DB, email string, otp int) bool {
	_, err := db.Exec(
		"INSERT INTO user_otp (email, otp, generated_at) VALUES ($1, $2, NOW()) ON CONFLICT (email) DO UPDATE SET otp = $2 , generated_at = NOW()",
		email, otp)
	return err == nil
}
func GetOTP(db *sql.DB, email string) (int, time.Time, error) {
	var otp int
	var timestamp time.Time
	err := db.QueryRow("SELECT otp, generated_at FROM users_otp WHERE email=$1", email).Scan(&otp, &timestamp)
	return otp, timestamp, err
}
func DeleteOTP(db *sql.DB, email string) bool {
	_, err := db.Exec("DELETE FROM users_otp WHERE email=$1", email)
	return err == nil
}