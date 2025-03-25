⚡ Energy Management System
A distributed microservices-based platform for managing users and smart energy devices. Built with Spring Boot, React, Docker, and secured via JWT.

🔧 Tech Stack
Backend: Spring Boot (Java), RabbitMQ (message broker)

Frontend: React

Database: MySQL

Containerization: Docker

Security: JWT + Spring Security

🧩 Architecture
Microservices:

User Service – CRUD + auth for users (admin/client)

Device Service – Manage energy devices & user-device mapping

Monitor Service – Track and store device measurements

Communication: REST + RabbitMQ

Frontend: Intuitive React interface

🗃 Database Structure
User DB – UUID, name, password, role

Device DB – UUID, description, address, max hourly consumption

Mapping – Links users to multiple devices

🚀 Deployment
All services and databases are containerized with Docker:

user-service, device-service, monitor-service

frontend

mysql instances for each service

🔐 Security
JWT-based authentication and authorization

Role-based access control (admin/client)

Secure inter-service communication via token validation

📬 API Overview
User Service
POST /user/login – Login with JWT

GET /user/getUsers – List users

POST /user/addUserGlobal – Create user + sync

DELETE /user/deleteUser/{id} – Delete user

Device Service
GET /device/getDevices – List devices

POST /device/insertDevice – Add device

POST /device/addUserToDevice/{userId}/{deviceId} – Assign device

Monitor Service
POST /monitor/createMeasurement – Add measurement

GET /monitor/getAllMeasurements – Get data logs

