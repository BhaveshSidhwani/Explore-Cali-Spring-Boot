# Explore California Spring Boot tutorial project

## Services

- **explorecali-jpa:** Service to manage Tours and Packages using Spring Boot JPA with MySQL
- **explorecali-images:** Service to manage Image data using Spring Boot JPA with MongoDB
- **explorecali-gateway:** Service to route incoming request to appropriate microservice. Uses Spring Cloud Gateway and Spring Security

## Command to start up

`mvn clean compile jib:dockerBuild && docker-compose up`

then make API calls on `http://localhost:8080`

### Exposed API Endpoints

1. Start individual services inside service directory: `mvn clean install spring-boot:run`
1. Visit `http://localhost:8082/swagger-ui/index.html` for 'explorecali-jpa' or `http://localhost:8081/swagger-ui/index.html` for 'explorecali-images'
