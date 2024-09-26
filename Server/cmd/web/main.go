package main

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/config"
	"github.com/itsNavinSingh/AdminConnect/Server/internal/handler"
	"github.com/itsNavinSingh/AdminConnect/Server/internal/render"
	"github.com/joho/godotenv"
	_ "github.com/lib/pq"
)
const portNumber = ":5000"
var app config.AppConfig
func main() {
	app.Inproduction = false
	tc, err := render.CreateTemplateCache()
	if err != nil {
		log.Fatal(err)
	}
	err = godotenv.Load()
	if err != nil {
		log.Fatal("Error Loading Environment file")
	}
	app.TemplateCache = tc
	app.Semail = os.Getenv("EMAIL")
	app.Spassword = os.Getenv("E_PASSWORD")
	app.JWTKey = []byte(os.Getenv("JWT_KEY"))
	databaseDetails := fmt.Sprintf("user=%s password=%s dbname=%s sslmode=%s", os.Getenv("DB_USERNAME"), os.Getenv("DB_PASSWORD"), os.Getenv("DB_NAME"), os.Getenv("DB_SSL_MODE"))
	app.DataBase, err = sql.Open("postgres", databaseDetails)
	if err != nil {
		log.Fatal(err)
	}
	defer app.DataBase.Close()
	app.UseCache = false
	repo := handler.NewRepo(&app)
	handler.NewHandlers(repo)
	render.NewTemplates(&app)
	fmt.Println(fmt.Sprintf("Starting application on port %s", portNumber))
	srv := &http.Server{
		Addr: portNumber,
		Handler: Routes(&app),
	}
	err = srv.ListenAndServe()
	if err != nil {
		log.Fatal(err)
	}
}