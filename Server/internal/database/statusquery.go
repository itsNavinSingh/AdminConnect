package database

import (
	"database/sql"

	"github.com/itsNavinSingh/AdminConnect/Server/internal/model"
)

func GetOngoingQuery(db *sql.DB, userId int) model.Queries {
	var output model.Queries
	rows, err := db.Query("SELECT registration_no, created_at, current_status FROM status WHERE userid=$1 AND current_status != $2", userId, "Completed")
	if err != nil {
		return output
	}
	defer rows.Close()
	for rows.Next(){
		var query model.Query
		if rows.Scan(&query.RegistrationId, &query.Date, &query.Status) == nil {
			output.Data = append(output.Data, query)
		}
	}
	return output
}