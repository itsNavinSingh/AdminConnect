package handler

import (
	"encoding/json"
	"net/http"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/database"
)

func (m *Repository) History(w http.ResponseWriter, r *http.Request) {
	tokenString := r.Header.Get("Authorization")
	email, err := ValidateJWTToken(tokenString, m.App.JWTKey)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
	}
	// get userid from email
	var userId int
	userId, err = database.GetUserId(m.App.DataBase, email)
	if err != nil {
		http.Error(w, "Bad Request", http.StatusBadRequest)
		return
	}
	//get ongoing data from status table
	queries := database.GetHistoryQuery(m.App.DataBase, userId)
	// send the json data
	w.Header().Set("Content-Type", "application/json")
	out, _ := json.Marshal(queries)
	w.Write(out)
}