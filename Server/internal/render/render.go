package render

import (
	"bytes"
	"fmt"
	"html/template"
	"net/http"
	"path/filepath"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/config"
)

var app *config.AppConfig
var functions = template.FuncMap{}
func NewTemplates(a *config.AppConfig){
	app = a
}
func CreateTemplateCache()(map[string]*template.Template, error){
	myCache := map[string]*template.Template{}
	pages, err := filepath.Glob("./templates/*.page.tmpl")
	if err != nil {
		return myCache, err
	}
	for _, page := range pages{
		name := filepath.Base(page)
		ts, err := template.New(name).Funcs(functions).ParseFiles(page)
		if err != nil {
			return myCache, err
		}
		matches, err := filepath.Glob("./templates/*.layout.tmpl")
		if err != nil {
			return myCache, err
		}
		if len(matches) > 0 {
			ts, err = ts.ParseGlob("./templates/*.layout.tmpl")
			if err != nil {
				return myCache, err
			}
		}
		myCache[name] = ts
	}
	return myCache, nil
}
func RenderTemplate(w http.ResponseWriter, r *http.Request, tmpl string, td any){
	var tc map[string]*template.Template
	if app.UseCache {
		tc = app.TemplateCache
	}else{
		tc, _ = CreateTemplateCache()
	}
	buf := new(bytes.Buffer)
	_ = tc[tmpl].Execute(buf, td)
	_, err := buf.WriteTo(w)
	if err != nil {
		fmt.Println("Error Writing template to browser", err)
	}
}