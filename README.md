# Simple CRUD
CRUD simples para o PFC
## API Endpoints
The API provides the following endpoints:

```markdown
GET /product - Retrieve a list of all data.

POST /product - Register a new data.

PUT /product - Alter data.

DELETE /product/{id} - Inactivate data.
```

## Database
The project uses PostgresSQL as the database. The necessary database migrations are managed using Flyway.

To install PostgresSQL locally you can [click here](https://www.postgresql.org/download/).