package main

import (
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
	"github.com/itsNavinSingh/AdminConnect/Server/internal/config"
)

func Routes(app *config.AppConfig) http.Handler{
	mux := chi.NewRouter()
	mux.Use(middleware.Recoverer)
	// User API for Mobile App
	
	// Admin routes for Web App
	return mux
}