package handler

import (
	"encoding/json"
	"net/http"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/model"
	"golang.org/x/crypto/bcrypt"
)

func (m *Repository) ApiLogin(w http.ResponseWriter, r *http.Request) {
	var regReq model.Login
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
	if err != nil || !exists {
		status.Message = "Email is not Registered"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	var hashedPassword string
	err = m.App.DataBase.QueryRow("SELECT password FROM users WHERE email=$1", regReq.Email).Scan(&hashedPassword)
	if err != nil {
		status.Message = "Internal Error"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	err = bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(regReq.Password))
	if err != nil {
		status.Message = "Invalid Password"
		out, _ := json.Marshal(status)
		w.Write(out)
		return
	}
	status.Message, err = CreateJWTToken(regReq.Email, m.App.JWTKey)
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