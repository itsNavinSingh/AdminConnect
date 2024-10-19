package database

import (
	"database/sql"
	"time"
)

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