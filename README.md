# 🚀 Rick and Morty Explorer & Products - Boilerplate



---

## 🇬🇧 English

### 📖 Project Overview
This is a Full Stack application developed as a university project. It features a local Product CRUD and a Rick and Morty API proxy. Built with **Clean Code**, **Layered Architecture**, and **DevOps best practices**.

### 🏗️ Architecture
The system implements a microservices architecture with an API Gateway:

1.  **Client:** HTML5, Tailwind CSS, Vanilla JS (ES6 Modules).
2.  **API Gateway (Port 80):** Spring Cloud Gateway. Single entry point, handles routing and security. Hides the port from the URL.
3.  **Backend (Port 8081):** Spring Boot. Handles business logic, CRUD, and external API proxies. Protected from direct internet access.
4.  **Database (Port 5432):** PostgreSQL. Persistent data storage.

**Flow:** User → http://IP (Port 80) → API Gateway → Backend (8081, Internal) → PostgreSQL (5432)

### 🛠️ Tech Stack
*   **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Spring Cloud Gateway.
*   **Frontend:** HTML5, Tailwind CSS, Vanilla JavaScript (ES6 Modules).
*   **Database:** PostgreSQL 15.
*   **DevOps:** Docker, Docker Compose, AWS EC2, GitHub Actions (CI/CD).

### 🧹 Clean Code Principles
*   **SRP:** Gateway routes; Backend handles logic; Frontend renders.
*   **YAGNI:** Removed unused Pokémon integration.
*   **Modularity:** Frontend separated into `api.js`, `ui.js`, `utils.js`.
*   **Security:** API Gateway hides backend. Backend in internal Docker network.

### 🚀 How to Run (Local)
1.  Clone the repo.
2.  Navigate to root: `cd boilerplate`
3.  Run Docker Compose: `docker-compose up -d --build`
4.  Open browser at: `http://localhost`

### ☁️ Deployment (AWS EC2)
1.  Launch EC2 instance (Amazon Linux 2023).
2.  Install Docker & Docker Compose.
3.  Clone repo and run: `docker-compose up -d --build`
4.  **CI/CD:** GitHub Action deploys changes from `main` branch via SSH.

### 🔌 API Endpoints
*   **Products (CRUD):**
    *   `GET /api/products`
    *   `POST /api/products`
    *   `PUT /api/products/{id}`
    *   `DELETE /api/products/{id}`
*   **External Proxy:**
    *   `GET /api/rick-and-morty/characters?name={name}`
    *   `GET /api/rick-and-morty/locations`
    *   `GET /api/rick-and-morty/episodes`

---