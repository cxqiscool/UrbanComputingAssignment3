
## Prerequisites

- **Java 17** or newer
- **Maven 3.8** or newer
- **PostgreSQL**: Ensure PostgreSQL is running and accessible

## Setup Instructions

1.**Configure PostgreSQL**:
    - Create a PostgreSQL database (e.g., `sonitusdata`).
    - Update the database credentials in `src/main/resources/application.properties`.

2.**Install Dependencies**:
   ```bash
   mvn clean install
   ```

## Configuration

In `src/main/resources/application.properties`, update with your database details:

```properties
# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/sonitusdata
spring.datasource.username=<your_db_username>
spring.datasource.password=<your_db_password>
spring.jpa.hibernate.ddl-auto=update
```

## Running the Application

To start the application:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## API Usage

### Endpoint: `/api/monitors`

- **Method**: `GET`
- **Description**: Retrieves a list of all monitors stored in the database.
- **URL**: `http://localhost:8080/api/monitors`

Example request:
```bash
curl http://localhost:8080/api/monitors
```

Expected response format:
```json
[
  {
    "id": 1,
    "name": "Monitor 1",
    "location": "Dublin City Center",
    ...
  },
  ...
]
```

## Troubleshooting

- **Database Connection Issues**: Ensure PostgreSQL is running and the credentials in `application.properties` are correct.
