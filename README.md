# 🚀 Rick and Morty Explorer & Products - Boilerplate

[English](#english) | [Español](#español)

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

```text
User → http://IP (Port 80) → API Gateway → Backend (8081, Internal) → PostgreSQL (5432)
🛠️ Tech Stack
Backend: Java 17, Spring Boot 3, Spring Data JPA, Spring Cloud Gateway.

Frontend: HTML5, Tailwind CSS, Vanilla JavaScript (ES6 Modules).

Database: PostgreSQL 15.

DevOps: Docker, Docker Compose, AWS EC2, GitHub Actions (CI/CD).

🧹 Clean Code Principles
SRP: Gateway routes; Backend handles logic; Frontend renders.

YAGNI: Removed unused Pokémon integration.

Modularity: Frontend separated into api.js, ui.js, utils.js.

Security: API Gateway hides backend. Backend in internal Docker network.

🚀 How to Run (Local)
Clone the repo.

Navigate to root:

bash
cd boilerplate
Run Docker Compose:

bash
docker-compose up -d --build
Open browser at: http://localhost

☁️ Deployment (AWS EC2)
Launch EC2 instance (Amazon Linux 2023).

Install Docker & Docker Compose.

Clone repo and run:

bash
docker-compose up -d --build
CI/CD: GitHub Action deploys changes from main branch via SSH.

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

🇪🇸 Español
📖 Descripción del Proyecto
Aplicación Full Stack desarrollada como proyecto universitario. Incluye un CRUD de productos y un proxy para la API de Rick and Morty. Construida siguiendo Clean Code, Arquitectura en Capas y Buenas Prácticas DevOps.

🏗️ Arquitectura
El sistema implementa una arquitectura de microservicios con un API Gateway:

Cliente: HTML5, Tailwind CSS, Vanilla JS (Módulos ES6).

API Gateway (Puerto 80): Spring Cloud Gateway. Punto de entrada único, maneja enrutamiento y seguridad. Oculta el puerto de la URL.

Backend (Puerto 8081): Spring Boot. Maneja lógica de negocio, CRUD y proxies de APIs externas. Protegido del acceso directo.

Base de Datos (Puerto 5432): PostgreSQL. Almacenamiento persistente.

text
Usuario → http://IP (Puerto 80) → API Gateway → Backend (8081, Interno) → PostgreSQL (5432)
🛠️ Stack Tecnológico
Backend: Java 17, Spring Boot 3, Spring Data JPA, Spring Cloud Gateway.

Frontend: HTML5, Tailwind CSS, Vanilla JavaScript (Módulos ES6).

Base de Datos: PostgreSQL 15.

DevOps: Docker, Docker Compose, AWS EC2, GitHub Actions (CI/CD).

🧹 Clean Code y Principios
SRP: El Gateway enruta; el Backend maneja lógica; el Frontend renderiza.

YAGNI: Se eliminó la integración de Pokémon.

Modularidad: Frontend separado en api.js, ui.js, utils.js.

Seguridad: API Gateway oculta el backend. Backend en red interna de Docker.

🚀 Cómo Ejecutar (Local)
Clona el repositorio.

Navega a la raíz:

bash
cd boilerplate
Ejecuta Docker Compose:

bash
docker-compose up -d --build
Abre el navegador en: http://localhost

☁️ Despliegue (AWS EC2)
Lanza una instancia EC2 (Amazon Linux 2023).

Instala Docker y Docker Compose.

Clona el repo y ejecuta:

bash
docker-compose up -d --build
CI/CD: El GitHub Action despliega los cambios de main vía SSH.

🔌 Endpoints de la API
Productos (CRUD):

GET /api/products

POST /api/products

PUT /api/products/{id}

DELETE /api/products/{id}

Proxy Externo:

GET /api/rick-and-morty/characters?name={name}

GET /api/rick-and-morty/locations

GET /api/rick-and-morty/episodes

Autor: Fernanda M. Llanos
Universidad: [Nombre de tu Universidad]
Fecha: Octubre 2026