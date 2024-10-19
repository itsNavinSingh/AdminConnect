package handler

import (
	"fmt"
	"math/rand"
	"net/smtp"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/database"
)


func GenerateOTP() int {
	return rand.Intn(1000000)
}


func SendEmailOTP(Semail, Spassword, Uemail string, otp int) error {
	smtpServer := "smtp.example.com" // to do (change server)
	auth := smtp.PlainAuth("", Semail, Spassword, smtpServer)
	to := []string{Uemail}
	msg := []byte("Subject: Your OTP for AdminConnect\n\nYour OTP for AdminConnect is " + fmt.Sprintf("%06d", otp) + "\nPlease do not share OTP with anyone.")
	err := smtp.SendMail(smtpServer+":587", auth, Semail, to, msg)
	return err
}

func (m *Repository)SendOTP(uemail string) error {
	otp := GenerateOTP()
	
	if !database.SendOTP(m.App.DataBase, uemail, otp) {
		return fmt.Errorf("Failed to generate otp")
	}
	err := SendEmailOTP(m.App.Semail, m.App.Spassword, uemail, otp)
	return err
}