package database

import (
	"database/sql"

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
func GetUserId(db *sql.DB, email string) (int, error) {
	var id int = 0
	err := db.QueryRow("SELECT userid FROM users WHERE email=$1", email).Scan(&id)
	return id, err
}