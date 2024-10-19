package model
type Register struct {
	Name string `json:"Name"`
	Phone int `json:"Phone"`
	Email string `json:"Email"`
	Password string `json:"Password"`
	Course string `json:"Course"`
	CourseYear uint16 `json:"CourseYear"`
	CRoll int16 `json:"CRoll"`
	URoll int `json:"URoll"`
}
type ApiStatus struct {
	Success bool `json:"Success"`
	Message string `json:"Message"`
}
type OTPVerify struct {
	Email string `json:"email"`
	OTP int `json:"otp"`
}
type Login struct {
	Email string `json:"email"`
	Password string `json:"password"`
}
type Query struct {
	RegistrationId int `json:"registrationId"`
	Date string `json:"date"`
	Status string `json:"status"`
}
type Queries struct {
	Data []Query `json:"data"`
}