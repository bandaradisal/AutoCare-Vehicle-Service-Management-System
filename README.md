# AutoCare Vehicle Service & Maintenance Management System

## Project Overview

AutoCare is a Vehicle Service and Maintenance Management System developed for the Data Management 2 coursework.

The system demonstrates a hybrid database architecture by integrating an Oracle relational database with MongoDB.

Oracle Database is used for structured and transactional information such as customers, vehicles, service bookings, spare parts, invoices, payments and application users.

MongoDB is used for flexible operational information such as job cards, technician notes, service history, customer complaints and feedback, and diagnostic summaries.

The application is developed using Java Spring Boot with Thymeleaf for the user interface and Spring Security for authentication and role-based access control.

---

## Main Features

The AutoCare system supports:

- Customer management
- Vehicle registration management
- Service booking management
- Technician management
- Spare-part inventory management
- Invoice management
- Invoice-item management
- Payment management
- Vehicle job-card management
- Technician notes
- Vehicle service-history records
- Customer complaints and feedback
- Diagnostic summaries
- User authentication
- Role-based authorization
- Oracle and MongoDB integration
- PL/SQL procedures
- PL/SQL functions
- Database triggers
- Explicit cursors
- Database indexes
- MongoDB schema validation
- MongoDB indexes

---

## Technology Stack

### Backend

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Data MongoDB
- Spring Security
- Hibernate
- Maven

### Frontend

- Thymeleaf
- HTML
- CSS

### Relational Database

- Oracle Database Free
- Oracle SQL Developer
- JDBC
- PL/SQL

### NoSQL Database

- MongoDB Community Server
- MongoDB Compass
- Spring Data MongoDB

### Development Tools

- Visual Studio Code
- Git
- GitHub
- Docker Desktop
- PowerShell

---

# System Architecture

The AutoCare application uses a hybrid database architecture.

```text
                     USER
                      |
                      v
               WEB BROWSER
                      |
                      v
              THYMELEAF UI
                      |
                      v
              SPRING SECURITY
                      |
                      v
           SPRING BOOT APPLICATION
                      |
        +-------------+-------------+
        |             |             |
        v             v             v
   CONTROLLER       SERVICE      REPOSITORY
      LAYER          LAYER          LAYER
                                      |
                         +------------+------------+
                         |                         |
                         v                         v
                  SPRING DATA JPA          SPRING DATA MONGODB
                    / HIBERNATE
                         |                         |
                         v                         v
                 ORACLE DATABASE              MONGODB
                    FREEPDB1              autocare_mongodb