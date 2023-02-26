<h1 align="center" id="title">🍃 Spring boot REST API Crud 🍃</h1>

<p id="description"> implementation of a basic CRUD using RESTful principles.</p>

  
<h2>✨ Features</h2>

*   Get a list of clients given a name or a ID number
*   Create a new client with a main address
*   Edit a client's attributes
*   Delete a client
*   Create aditional addresses for a given client
*   Get all registered addresses for a given client
  
<h2>💻 Technologies</h2>

*   Spring Boot
*   Spring Data JPA
*   PostgreSQL Database

<h2>🛠️ Database configuration:</h2>

1. Create a Postgresql database with the name `test` .

2. Add the credentials to application properties

```
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=postgres
spring.datasource.password=password
```

<h2>🚀 Usage:</h2>

```bash
# Return all registered clients
GET /clientes

# Return a specific user -> id
GET /clientes/{id}

# Return a list of clients -> name
GET /clientes/{name}

# Create a client -> client
POST /clientes

# Update a client -> client
PUT /clientes

# Delete a client -> id
DELETE /clientes/{id}
```
```bash
# Return all registered addresses -> client's id
GET /direcciones/{id}

# Create an adittional address -> client's id
POST /direcciones/{id}
```
