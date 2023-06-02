<h1 align="center" id="title">🍃 Client Management System REST API 🍃</h1>

<p id="description"> RESTful API for a client management system developed with Spring Boot in Java.</p>
  
<h2>✨ Features</h2>

*   Get a list of all clients
*   Get a list of clients given a name or a ID number
*   Create a new client with a main address
*   Edit a client's attributes
*   Delete a client
*   Create aditional addresses for a given client
*   Get all registered addresses for a given client
  
<h2>💻 Technologies</h2>

*   Spring Boot
*   Spring Data JPA
*   Hibernate
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
# Get all registered clients
GET /clientes/

# Get a specific user by id
GET /clientes/?id={id}

# Get a list of clients by name
GET /clientes/?nombre={name}

# Create a client
POST /clientes/

# Modify a client by id
PUT /clientes/{id}

# Delete a client by id
DELETE /clientes/{id}
```
```bash
# Get all registered addresses by client's id
GET /direcciones/{id}

# Create an adittional address by client's id
POST /direcciones/{id}
```
