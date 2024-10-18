package handler

import (
	"fmt"
	"math/rand"
	"net/smtp"
)


func GenerateOTP() string {
	return fmt.Sprintf("%06d", rand.Intn(1000000))
}


func SendEmailOTP(Semail, Spassword, Uemail, otp string) error {
	smtpServer := "smtp.example.com" // to do (change server)
	auth := smtp.PlainAuth("", Semail, Spassword, smtpServer)
	to := []string{Uemail}
	msg := []byte("Subject: Your OTP for AdminConnect\n\nYour OTP for AdminConnect is " + otp + "\nPlease do not share OTP with anyone.")
	err := smtp.SendMail(smtpServer+":587", auth, Semail, to, msg)
	return err
}

func (m *Repository)SendOTP(uemail string) error {
	otp := GenerateOTP()
	_, err := m.App.DataBase.Exec(
		"INSERT INTO user_otp (email, otp, generated_at) VALUES ($1, $2, NOW()) ON CONFLICT (email) DO UPDATE SET otp = $2 , generated_at = NOW()",
		uemail, otp)
	if err != nil {
		return err
	}
	err = SendEmailOTP(m.App.Semail, m.App.Spassword, uemail, otp)
	return err
}