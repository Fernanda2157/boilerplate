Project Overview
This is a Full Stack application developed as a university project. It features a local Product CRUD (Create, Read, Update, Delete) and an Integration Service that consumes external APIs (Rick and Morty and PokeAPI) using a "Nested Select" pattern. The project is built following Clean Code principles and Layered Architecture.

🛠️ Tech Stack
Backend: Java 17, Spring Boot 3, Spring Data JPA, H2 Database (In-Memory).

Frontend: HTML5, Tailwind CSS (via CDN), Vanilla JavaScript (ES6 Modules).

Tools: Maven, Jira (Scrum), GitHub (Git Flow & Conventional Commits).

🏗️ Architecture & Clean Code (Backend)
The backend strictly follows the Single Responsibility Principle (SRP):

controller: Handles HTTP requests and responses (REST).

service: Contains business logic and orchestrates the "Nested Select" with caching (ConcurrentHashMap).

client: Pure HTTP clients (RestTemplate) dedicated solely to external API communication.

dto / model: Separation of data transfer objects and database entities.

exception: Global exception handling using @RestControllerAdvice.

🔌 API Endpoints
Products (CRUD):

GET /api/products

POST /api/products

PUT /api/products/{id}

DELETE /api/products/{id}

External Proxy:

GET /api/rick-and-morty/characters?name={name}

GET /api/rick-and-morty/locations

GET /api/rick-and-morty/episodes

Nested Select Integration:

GET /api/integration/characters-with-pokemon

🚀 How to Run
Clone the repository.

Open the project in IntelliJ IDEA or VS Code.

Run the Spring Boot application:

bash
./mvnw spring-boot:run
Open your browser at http://localhost:8080.
