Box Delivery Service API
Overview
A RESTful API for managing delivery boxes that carry small items. Built with Java and Spring Boot, it uses an in-memory H2 database and handles JSON input/output. The API supports creating boxes, loading items with validation, retrieving loaded items, listing available boxes, and checking battery levels.
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

Build the Project

Clone the repository:git clone https://github.com/your-username/box-delivery-api.git
cd box-delivery-api


Build with Maven:mvn clean install


Compiles code, runs tests, and creates target/boxApiService-0.0.1-SNAPSHOT.jar.



Run the Application

Start the application:mvn spring-boot:run


Runs on http://localhost:8080.


Access the H2 database console (optional):
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa, Password: (empty)


Access Swagger API documentation:
URL: http://localhost:8080/swagger-ui/index.html



API Endpoints

POST /api/v1/boxes: Create a box (e.g., {"txref":"BOX004","weightLimit":450.0,"batteryCapacity":70}).
POST /api/v1/boxes/{id}/load: Load items into a box (e.g., [{"name":"item2","weight":50.0,"code":"CODE2"}]).
GET /api/v1/boxes/{id}/items: Get loaded items for a box.
GET /api/v1/boxes/available: Get available boxes (IDLE, battery >= 25%).
GET /api/v1/boxes/{id}/battery: Get a box's battery level.

Preloaded Data
On startup, the application preloads:

Box: txref="BOX001", weightLimit=400.0, batteryCapacity=80, state=IDLE.
Box: txref="BOX002", weightLimit=300.0, batteryCapacity=20, state=IDLE.
Box: txref="BOX003", weightLimit=500.0, batteryCapacity=90, state=LOADED, with item (name="item_1", weight=100.0, code="CODE1").

Testing
Run tests:
mvn test


Includes unit tests (BoxServiceImplTest) and integration tests (BoxControllerTest) for all functionality.

Notes

Validation: Uses Jakarta Bean Validation for input DTOs.
Error Handling: Custom exceptions (e.g., LowBatteryException) return JSON error responses.
Swagger: Interactive API docs at http://localhost:8080/swagger-ui/index.html.

Troubleshooting

Build Issues: Ensure Java 17 and Maven are installed. Run mvn clean install to debug.
Run Issues: Check application.properties for correct settings:spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
server.port=8080
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
springdoc.swagger-ui.enabled=true


