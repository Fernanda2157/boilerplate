# Rick and Morty Explorer & Products

A full-stack web application built with Spring Boot (REST API) and a user interface developed with Vanilla JavaScript and Tailwind CSS. It features a complete CRUD for products using PostgreSQL and an integration service that combines data from the Rick and Morty and Pokémon public APIs through a nested selection mechanism.
Aquí tienes todo el contenido traducido al inglés y formateado estrictamente en sintaxis **Markdown para README** listo para copiar y pegar directamente en tu archivo `README.md`.

```markdown
# Rick and Morty Explorer & Products

A full-stack web application built with Spring Boot (REST API) and a user interface developed with Vanilla JavaScript and Tailwind CSS. It features a complete CRUD for products using PostgreSQL and an integration service that combines data from the Rick and Morty and Pokémon public APIs through a nested selection mechanism.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [REST API](#rest-api)
- [Nested Select (Integration Service)](#nested-select-integration-service)
- [Frontend](#frontend)
- [Installation and Setup](#installation-and-setup)
- [Project Management](#project-management)
- [Applied Best Practices](#applied-best-practices)
- [Future Improvements](#future-improvements)
- [Author](#author)

---

## Features

- **Product CRUD:** List, create, edit, and delete products stored in a PostgreSQL database.
- **Rick and Morty Explorer:** Browse characters, locations, and episodes requested via the backend proxy (the client browser never calls the external APIs directly).
- **Integration Service (Nested Select):** Fetches characters from Rick and Morty and dynamically maps each character's ID to fetch its corresponding Pokémon from PokéAPI.
- **Card-Based UI:** Displays items using responsive cards; clicking a card opens a detailed view modal.
- **Search Bar with Debounce (4.5s):** Automatically filters results by name 4.5 seconds after the user stops typing.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 4.1.1, Spring Web, Spring Data JPA, RestTemplate |
| **Database** | PostgreSQL |
| **Frontend** | HTML5, Vanilla JavaScript (ES Modules), Tailwind CSS (CDN), Font Awesome |
| **Build Tool** | Apache Maven |
| **Management & Versioning** | Jira (Scrum), Confluence, Git, GitHub |

---

## Architecture

```text
Browser (Vanilla JS + Tailwind)
        │  fetch /api/...
        ▼
Spring Boot
  Controller ──► Service ──► Repository ──► PostgreSQL
                    │
                    └──► Client (RestTemplate) ──► rickandmortyapi.com · pokeapi.co

```

The backend follows a layered architecture where each package maintains a single responsibility:

```text
src/main/java/com/example/boilerplate
├── BoilerplateApplication.java
├── client/          # External HTTP client integrations (RickAndMortyClient, PokemonClient)
├── config/          # Bean configurations (RestTemplateConfig with timeouts)
├── controller/      # REST Endpoints (ProductController, RickAndMortyController, IntegrationController)
├── dto/             # Data Transfer Objects (ProductDTO, CharacterDTO, PokemonDTO, CharacterPokemonDTO)
├── exception/       # Domain exceptions and global handling (ResourceNotFoundException, GlobalExceptionHandler)
├── model/           # JPA Entities (Product)
├── repository/      # Persistence layer (ProductRepository)
└── service/         # Business logic layer (Interfaces and implementations)

```

---

## REST API

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/products` | Retrieve all local products |
| `GET` | `/api/products/{id}` | Retrieve a product by ID |
| `POST` | `/api/products` | Create a new product |
| `PUT` | `/api/products/{id}` | Update an existing product |
| `DELETE` | `/api/products/{id}` | Delete a product |
| `GET` | `/api/rick-and-morty/characters` | Fetch Rick and Morty characters (Proxy) |
| `GET` | `/api/rick-and-morty/locations` | Fetch locations |
| `GET` | `/api/rick-and-morty/episodes` | Fetch episodes |
| `GET` | `/api/integration/characters-with-pokemon` | Fetch combined characters with Pokémon (Nested Select) |

### Sample Payload for Creating a Product (`POST /api/products`):

```json
{
  "name": "Gaming Monitor 27''",
  "description": "IPS 144Hz 1ms",
  "price": 199.5
}

```

**Error Handling:** Missing resources return `404 Not Found`. Unexpected errors return `500 Internal Server Error` with a structured payload: `{ "error": "..." }`.

---

## Nested Select (Integration Service)

`GET /api/integration/characters-with-pokemon` executes dependent external requests:

1. **Request 1:** Fetches character data from the Rick and Morty API.
2. **Request 2 (Nested):** For each character, uses its `id` to query PokéAPI for the matching Pokémon ID.
3. Combines both responses into a unified list.

### Design Decisions:

* **Rate Limit Safeguard:** Restricts processing to the first 10 characters to prevent API rate limits.
* **In-Memory Caching:** Prevents redundant external HTTP calls for previously queried Pokémon IDs.
* **Timeouts:** Configured with a 3-second connect/read timeout to prevent hanging threads.
* **Fallback Mechanism:** Returns `"Unknown"` for Pokémon fields if PokéAPI fails or times out.

### Sample Response:

```json
[
  {
    "character": { "id": 1, "name": "Rick Sanchez", "species": "Human" },
    "pokemon": { "name": "bulbasaur", "height": 7 }
  },
  {
    "character": { "id": 2, "name": "Morty Smith", "species": "Human" },
    "pokemon": { "name": "ivysaur", "height": 10 }
  }
]

```

> *Note: Pokémon height is returned in decimeters (e.g., 7 = 0.7m); converted in the frontend layer.*

---

## Frontend

Located in `src/main/resources/static/`, served directly by Spring Boot (eliminating CORS issues):

```text
static/
├── index.html
└── js/
    ├── main.js             # Orchestrator: UI state and component initialization
    ├── config.js           # API route mappings and tab display metadata
    ├── services/api.js     # Centralized HTTP request handling (fetch API)
    ├── components/         # Modular UI elements (tabs, searchBar, cards, detailModal, productForm)
    └── utils/              # Helper utilities (debounce, escapeHtml, capitalize)

```

* **Tabs:** Characters, Locations, Episodes, Integration, and Local Products.
* **Cards & Detail View:** Clicking any item card opens a detailed modal view (closeable via button, backdrop click, or ESC key).
* **Debounced Search:** Features a status indicator ("Searching in 4.5s...") that delays execution until typing stops.
* **Product Management:** Full form integration for creating and updating items, with confirmation prompts prior to deletion.

---

## Installation and Setup

### Prerequisites

* **JDK 17** or higher
* **Maven 3.6.3** or higher
* **PostgreSQL** running locally

### 1. Clone the repository

```bash
git clone [https://github.com/Fernanda2157/boilerplate.git](https://github.com/Fernanda2157/boilerplate.git)
cd boilerplate/boilerplate     # Folder containing pom.xml

```

### 2. Create the Database

```sql
CREATE DATABASE boilerplate_db;

```

### 3. Configure `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/boilerplate_db
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update

```

Set environment variables in PowerShell to secure credentials:

```powershell
$env:DB_USER="postgres"
$env:DB_PASSWORD="your_password"

```

### 4. Run the Application

```bash
mvn spring-boot:run

```

Once `Started BoilerplateApplication` appears in the logs, navigate to `http://localhost:8080`.

### 5. Verify API Endpoints

* `http://localhost:8080/api/products`
* `http://localhost:8080/api/rick-and-morty/characters`
* `http://localhost:8080/api/integration/characters-with-pokemon`

---

## Project Management

Developed using **Personal Scrum** (Scrum tailored for single developers) featuring 1-day sprints, a prioritized backlog in Jira, and a strict limit of 1 Work-In-Progress (WIP) story at a time.

### Backlog Epics:

| Epic | Objective |
| --- | --- |
| **Integration Service** | Rick and Morty API proxy & nested select implementation with PokéAPI |
| **Backend Refactoring & Clean Code** | Layered separation, centralized error handling, and timeout configuration |
| **Frontend Modularization** | Splitting monolithic HTML into ES modules and adding the Integration tab |
| **Quality & Closure** | Technical documentation and repository preparation |

### Version Control Guidelines

* **Branches:** `main` (production-ready stable builds) and `dev` (active integration branch).
* **Commit Format:** Conventional Commits linked with Jira issue keys:
* `feat(client): create external API clients [SCRUM-13]`
* `refactor: move ProductDTO and add exception handling [SCRUM-4] [SCRUM-6]`



---

## Applied Best Practices

### Backend

* Strict **layered architecture** enforcing separation of concerns.
* Constructor-based **dependency injection**.
* **DTOs** used exclusively across boundary layers to encapsulate JPA entities.
* Isolated HTTP client handlers with explicit network timeouts.
* Domain-specific exceptions handled globally via `@RestControllerAdvice`.
* In-memory caching mechanism to optimize repetitive external requests.

### Frontend

* Native **ES Modules** maintaining modular responsibility per file.
* Centralized UI configuration in `config.js`.
* Single source of truth for network operations (`api.js`).
* XSS prevention via HTML escaping (`escapeHtml`) before DOM insertion.

---

## Future Improvements

* Server-side filtering (`?name=`) and pagination for external API results.
* Refactoring price fields from `double` to `BigDecimal` for financial precision.
* Extracting Entity-DTO mappings to dedicated `ProductMapper` classes.
* Unit and integration testing suites using JUnit 5 and Mockito.
* Database migration support via Flyway.

---

## Author

**Maria Fernanda Llano Sambache**

Computer Science / Systems Engineering

*Universidad Central del Ecuador*

```

```
---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [REST API](#rest-api)
- [Nested Select (Integration Service)](#nested-select-integration-service)
- [Frontend](#frontend)
- [Installation and Setup](#installation-and-setup)
- [Project Management](#project-management)
- [Applied Best Practices](#applied-best-practices)
- [Future Improvements](#future-improvements)
- [Author](#author)

---

## Features

- **Product CRUD:** List, create, edit, and delete products stored in a PostgreSQL database.
- **Rick and Morty Explorer:** Browse characters, locations, and episodes requested via the backend proxy (the client browser never calls the external APIs directly).
- **Integration Service (Nested Select):** Fetches characters from Rick and Morty and dynamically maps each character's ID to fetch its corresponding Pokémon from PokéAPI.
- **Card-Based UI:** Displays items using responsive cards; clicking a card opens a detailed view modal.
- **Search Bar with Debounce (4.5s):** Automatically filters results by name 4.5 seconds after the user stops typing.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 4.1.1, Spring Web, Spring Data JPA, RestTemplate |
| **Database** | PostgreSQL |
| **Frontend** | HTML5, Vanilla JavaScript (ES Modules), Tailwind CSS (CDN), Font Awesome |
| **Build Tool** | Apache Maven |
| **Management & Versioning** | Jira (Scrum), Confluence, Git, GitHub |

---

## Architecture

```text
Browser (Vanilla JS + Tailwind)
        │  fetch /api/...
        ▼
Spring Boot
  Controller ──► Service ──► Repository ──► PostgreSQL
                    │
                    └──► Client (RestTemplate) ──► rickandmortyapi.com · pokeapi.co
The backend follows a layered architecture where each package maintains a single responsibility:Plaintextsrc/main/java/com/example/boilerplate
├── BoilerplateApplication.java
├── client/          # External HTTP client integrations (RickAndMortyClient, PokemonClient)
├── config/          # Bean configurations (RestTemplateConfig with timeouts)
├── controller/      # REST Endpoints (ProductController, RickAndMortyController, IntegrationController)
├── dto/             # Data Transfer Objects (ProductDTO, CharacterDTO, PokemonDTO, CharacterPokemonDTO)
├── exception/       # Domain exceptions and global handling (ResourceNotFoundException, GlobalExceptionHandler)
├── model/           # JPA Entities (Product)
├── repository/      # Persistence layer (ProductRepository)
└── service/         # Business logic layer (Interfaces and implementations)
REST APIMethodEndpointDescriptionGET/api/productsRetrieve all local productsGET/api/products/{id}Retrieve a product by IDPOST/api/productsCreate a new productPUT/api/products/{id}Update an existing productDELETE/api/products/{id}Delete a productGET/api/rick-and-morty/charactersFetch Rick and Morty characters (Proxy)GET/api/rick-and-morty/locationsFetch locationsGET/api/rick-and-morty/episodesFetch episodesGET/api/integration/characters-with-pokemonFetch combined characters with Pokémon (Nested Select)Sample Payload for Creating a Product (POST /api/products):JSON{
  "name": "Gaming Monitor 27''",
  "description": "IPS 144Hz 1ms",
  "price": 199.5
}
Error Handling: Missing resources return 404 Not Found. Unexpected errors return 500 Internal Server Error with a structured payload: { "error": "..." }.Nested Select (Integration Service)GET /api/integration/characters-with-pokemon executes dependent external requests:Request 1: Fetches character data from the Rick and Morty API.Request 2 (Nested): For each character, uses its id to query PokéAPI for the matching Pokémon ID.Combines both responses into a unified list.Design Decisions:Rate Limit Safeguard: Restricts processing to the first 10 characters to prevent API rate limits.In-Memory Caching: Prevents redundant external HTTP calls for previously queried Pokémon IDs.Timeouts: Configured with a 3-second connect/read timeout to prevent hanging threads.Fallback Mechanism: Returns "Unknown" for Pokémon fields if PokéAPI fails or times out.Sample Response:JSON[
  {
    "character": { "id": 1, "name": "Rick Sanchez", "species": "Human" },
    "pokemon": { "name": "bulbasaur", "height": 7 }
  },
  {
    "character": { "id": 2, "name": "Morty Smith", "species": "Human" },
    "pokemon": { "name": "ivysaur", "height": 10 }
  }
]
Note: Pokémon height is returned in decimeters (e.g., 7 = 0.7m); converted in the frontend layer.FrontendLocated in src/main/resources/static/, served directly by Spring Boot (eliminating CORS issues):Plaintextstatic/
├── index.html
└── js/
    ├── main.js             # Orchestrator: UI state and component initialization
    ├── config.js           # API route mappings and tab display metadata
    ├── services/api.js     # Centralized HTTP request handling (fetch API)
    ├── components/         # Modular UI elements (tabs, searchBar, cards, detailModal, productForm)
    └── utils/              # Helper utilities (debounce, escapeHtml, capitalize)
Tabs: Characters, Locations, Episodes, Integration, and Local Products.Cards & Detail View: Clicking any item card opens a detailed modal view (closeable via button, backdrop click, or ESC key).Debounced Search: Features a status indicator ("Searching in 4.5s...") that delays execution until typing stops.Product Management: Full form integration for creating and updating items, with confirmation prompts prior to deletion.Installation and SetupPrerequisitesJDK 17 or higherMaven 3.6.3 or higherPostgreSQL running locally1. Clone the repositoryBashgit clone [https://github.com/Fernanda2157/boilerplate.git](https://github.com/Fernanda2157/boilerplate.git)
cd boilerplate/boilerplate     # Folder containing pom.xml
2. Create the DatabaseSQLCREATE DATABASE boilerplate_db;
3. Configure src/main/resources/application.propertiesPropertiesspring.datasource.url=jdbc:postgresql://localhost:5432/boilerplate_db
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
Set environment variables in PowerShell to secure credentials:PowerShell$env:DB_USER="postgres"
$env:DB_PASSWORD="your_password"
4. Run the ApplicationBashmvn spring-boot:run
Once Started BoilerplateApplication appears in the logs, navigate to http://localhost:8080.5. Verify API Endpointshttp://localhost:8080/api/productshttp://localhost:8080/api/rick-and-morty/charactershttp://localhost:8080/api/integration/characters-with-pokemonProject ManagementDeveloped using Personal Scrum (Scrum tailored for single developers) featuring 1-day sprints, a prioritized backlog in Jira, and a strict limit of 1 Work-In-Progress (WIP) story at a time.Backlog Epics:EpicObjectiveIntegration ServiceRick and Morty API proxy & nested select implementation with PokéAPIBackend Refactoring & Clean CodeLayered separation, centralized error handling, and timeout configurationFrontend ModularizationSplitting monolithic HTML into ES modules and adding the Integration tabQuality & ClosureTechnical documentation and repository preparationVersion Control GuidelinesBranches: main (production-ready stable builds) and dev (active integration branch).Commit Format: Conventional Commits linked with Jira issue keys:feat(client): create external API clients [SCRUM-13]refactor: move ProductDTO and add exception handling [SCRUM-4] [SCRUM-6]Applied Best PracticesBackendStrict layered architecture enforcing separation of concerns.Constructor-based dependency injection.DTOs used exclusively across boundary layers to encapsulate JPA entities.Isolated HTTP client handlers with explicit network timeouts.Domain-specific exceptions handled globally via @RestControllerAdvice.In-memory caching mechanism to optimize repetitive external requests.FrontendNative ES Modules maintaining modular responsibility per file.Centralized UI configuration in config.js.Single source of truth for network operations (api.js).XSS prevention via HTML escaping (escapeHtml) before DOM insertion.Future ImprovementsServer-side filtering (?name=) and pagination for external API results.Refactoring price fields from double to BigDecimal for financial precision.Extracting Entity-DTO mappings to dedicated ProductMapper classes.Unit and integration testing suites using JUnit 5 and Mockito.Database migration support via Flyway.AuthorMaria Fernanda Llano SambacheComputer Science / Systems EngineeringUniversidad Central del Ecuador
