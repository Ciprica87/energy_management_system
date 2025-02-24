⚡ Energy Management System
The Energy Management System is a distributed application built using microservices to efficiently manage users and smart energy devices. This system allows administrators and clients to manage user accounts, monitor energy consumption, and maintain device allocations through a secure and scalable platform.

🚀 Features

👤 User Management
Role-based authentication (Admin/Client).
CRUD operations for user accounts (ID, name, password, role).
User-device assignment and management.

🔌 Device Management
CRUD operations for smart energy devices (ID, name, max hourly consumption).
Assign and unassign users to devices.
Real-time monitoring of energy consumption.

📊 Monitoring & Reporting
Track energy usage for each device.
Generate and view historical measurements.
Export data in various formats.

🛠️ Tech Stack
Backend: Java Spring Boot (Microservices)
Frontend: React.js
Database: MySQL
Containerization: Docker
Message Broker: RabbitMQ

🏗️ Architecture
The system follows a microservice-based architecture, ensuring scalability and maintainability. Key components include:

User Service: Manages user accounts and authentication.
Device Service: Manages smart devices and user-device relationships.
Monitoring Service: Tracks energy consumption and device activity.
RabbitMQ: Facilitates communication between microservices.

Each microservice is deployed in a separate Docker container, communicating via REST APIs.
