package handler

import (
	"database/sql"
	"net/http"
)

func (m *Repository) Ongoing(w http.ResponseWriter, r *http.Request) {
	tokenString := r.Header.Get("Authorization")
	email, err := ValidateJWTToken(tokenString, m.App.JWTKey)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
	}
	rows * sql.Rows
	// TO Do
	rows, err = m.App.DataBase.Query()
}
