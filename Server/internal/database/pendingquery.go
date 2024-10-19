package database

import (
	"database/sql"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/model"
)

func StoreToTemp(db *sql.DB, regReq *model.Register, hashedPassword []byte) bool {
	_, err := db.Exec(
		"INSERT INTO pending_users (email, password, phone, course, course_year, croll, uroll, name) VALUES ($1, $2, $3, $4, $5, $6, $7, $8) ON CONFLICT (email) DO UPDATE SET password = $2, phone = $3, course = $4, course_year = $5, croll = $6, uroll = $7, name = $8",
		regReq.Email, hashedPassword, regReq.Phone, regReq.Course, regReq.CourseYear, regReq.CRoll, regReq.URoll, regReq.Name)
	return err == nil
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