package handler

import (
	"fmt"
	"time"
	"github.com/golang-jwt/jwt/v5"
)

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

func ValidateJWTToken(tokenStr string, jwtKey []byte) (string, error) {
	claims := Claims{}
	token, err := jwt.ParseWithClaims(tokenStr, claims, func(token *jwt.Token)(interface{}, error){
		return jwtKey, nil
	})
	if err != nil {
		return "", err
	}
	if !token.Valid {
		return "", fmt.Errorf("invalid token")
	}
	return claims.Username, nil
}