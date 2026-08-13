# AutoCare Oracle Database Design

## 1. Database Information

Database Service: FREEPDB1

Schema/User: AUTOCARE

Default Tablespace: USERS

Temporary Tablespace: TEMP

Database Type: Oracle Relational Database

## 2. Purpose

Oracle Database stores the structured and transactional information of the AutoCare Vehicle Service and Maintenance Management System.

It is used for customer details, vehicle registration, service bookings, technicians, spare-part inventory, invoices, invoice items, payments and application user accounts.

## 3. Oracle Tables

### CUSTOMER
Stores customer personal and contact information.

### VEHICLE
Stores vehicle registration and vehicle information.

Relationship:

CUSTOMER 1 : M VEHICLE

### SERVICE_BOOKING
Stores vehicle service bookings.

Relationship:

VEHICLE 1 : M SERVICE_BOOKING

### TECHNICIAN
Stores technician details, specialization, experience and current status.

### SPARE_PART
Stores spare-part information, prices, available quantities and reorder levels.

### INVOICE
Stores invoices generated for service bookings.

Relationship:

SERVICE_BOOKING 1 : 0..1 INVOICE

### INVOICE_ITEM
Stores individual spare parts included in an invoice.

Relationships:

INVOICE 1 : M INVOICE_ITEM

SPARE_PART 1 : M INVOICE_ITEM

### PAYMENT
Stores payments made for invoices.

Relationship:

INVOICE 1 : M PAYMENT

### APP_USER
Stores application user accounts and roles used by Spring Security.

## 4. Database Features

The Oracle implementation contains:

- Primary keys
- Foreign keys
- Unique constraints
- CHECK constraints
- Indexes
- Stored procedures
- Stored functions
- Database triggers
- Explicit cursors
- Sample data
- CRUD operations
- Invoice and payment processing
- Spare-part stock management

## 5. Application Integration

The Spring Boot application connects to Oracle through JDBC and Spring Data JPA/Hibernate.

Oracle provides the structured transactional part of the hybrid database architecture.