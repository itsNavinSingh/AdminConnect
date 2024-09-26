package handler

import (
	"fmt"
	"math/rand"
	"net/smtp"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

func GenerateOTP() string {
	rand.Seed(time.Now().UnixNano())
	return fmt.Sprintf("%06d", rand.Intn(1000000))
}
func SendOTP(Semail, Spassword, Uemail, otp string) error {
	smtpServer := "smtp.example.com"
	auth := smtp.PlainAuth("", Semail, Spassword, smtpServer)
	to := []string{Uemail}
	msg := []byte("Subject: Your OTP for AdminConnect\n\nYour OTP for AdminConnect is " + otp + "\nPlease do not share OTP with anyone.")
	err := smtp.SendMail(smtpServer+":587", auth, Semail, to, msg)
	return err
}

type Claims struct {
	Username string `json:"username"`
	jwt.RegisteredClaims
}
func CreateJWTToken(username string, jwtkey []byte) (string, error) {
	claims := &Claims{
		Username: username,
		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(time.Now().Add(25*24*time.Hour)),
		},
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	return token.SignedString(jwtkey)
}