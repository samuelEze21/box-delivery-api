Box Delivery Service API
Overview
A RESTful API for managing delivery boxes that carry small items, built with Java and Spring Boot. It uses an in-memory H2 database and handles JSON input/output. The API supports creating boxes, loading items with validation, retrieving loaded items, listing available boxes, and checking battery levels. Sample data is preloaded on startup.
Functional Requirements

Prevents loading a box with more weight than its capacity (weightLimit).
Prevents loading if the box's battery level is below 25%.

Non-Functional Requirements

Built with Java and Spring Boot.
Input/output in JSON format.
Buildable and runnable with Maven.
Preloads sample data on startup.
Includes unit and integration tests.

Prerequisites

Java 17+
Maven 3.8+
IntelliJ IDEA (recommended for Maven tool window)

Build the Project

Clone the repository:
git clone https://github.com/your-username/box-delivery-api.git
cd box-delivery-api


Open the project in IntelliJ IDEA:

File > Open > Select project directory.


Build using Maven in IntelliJ:

Open the Maven tool window (right-hand side, blue "M" icon).
Expand boxApiService > Lifecycle.
Double-click clean to clear the target directory.
Double-click install to compile, run tests, and generate target/boxApiService-0.0.1-SNAPSHOT.jar.
Verify the JAR exists in the target directory.



Run the Application

Run using Maven in IntelliJ:
In the Maven tool window, expand boxApiService > Plugins > spring-boot.
Double-click spring-boot:run to start the application.
The app runs on http://localhost:8080.


Verify functionality:
H2 Console: Open http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:testdb, Username: sa, Password: empty) to check preloaded data.
Swagger UI: Open http://localhost:8080/swagger-ui/index.html for API documentation.


Test endpoints (using curl, Postman, or Swagger):
POST /api/v1/boxes: Create a box (e.g., {"txref":"BOX004","weightLimit":450.0,"batteryCapacity":70}).
POST /api/v1/boxes/{id}/load: Load items (e.g., [{"name":"item2","weight":50.0,"code":"CODE2"}]).
GET /api/v1/boxes/{id}/items: Get loaded items.
GET /api/v1/boxes/available: Get available boxes (IDLE, battery >= 25%).
GET /api/v1/boxes/{id}/battery: Get battery level.




Testing
Run tests in IntelliJ:

In the Maven tool window, double-click boxApiService > Lifecycle > test.
Includes unit tests (BoxServiceImplTest) and integration tests (BoxControllerTest).

Notes

Validation: Uses Jakarta Bean Validation for DTOs.
Error Handling: Custom exceptions return JSON error responses.
Swagger: Interactive API docs at http://localhost:8080/swagger-ui/index.html.

Troubleshooting

Build Issues: Ensure Java 17 (java -version) and Maven (mvn -v) are installed. In IntelliJ, check File > Settings > Build, Execution, Deployment > Build Tools > Maven. Run clean and install again.

Run Issues: Ensure port 8080 is free (netstat -an | grep 8080). Verify application.properties:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
server.port=8080
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
springdoc.swagger-ui.enabled=true


Swagger Issues: Use http://localhost:8080/swagger-ui/index.html. Ensure Springdoc dependency in pom.xml.

