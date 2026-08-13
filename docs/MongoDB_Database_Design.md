# AutoCare MongoDB Database Design

## 1. Database Information

Database Name: autocare_mongodb

Database Type: MongoDB Document Database

Connection: localhost:27017

## 2. Purpose

MongoDB is used to store flexible and semi-structured information in the AutoCare Vehicle Service and Maintenance Management System.

It is suitable for job cards, technician notes, service history, customer complaints and feedback, and vehicle diagnostic information because these records may contain nested data and can change in structure over time.

## 3. MongoDB Collections

### job_cards

Stores vehicle service job cards.

Important fields include:

- job_card_id
- booking_id
- vehicle_id
- technician_id
- opened_date
- status
- tasks

The booking_id, vehicle_id and technician_id fields logically reference records stored in Oracle.

---

### technician_notes

Stores notes entered by technicians while servicing vehicles.

Important fields include:

- note_id
- job_card_id
- technician_id
- created_at
- notes

The job_card_id links the notes to a MongoDB job card.

The technician_id logically references the Oracle TECHNICIAN table.

---

### service_history

Stores previous vehicle service information.

Important fields include:

- history_id
- vehicle_id
- booking_id
- service_date
- service_type
- mileage
- summary
- parts_used

The vehicle_id and booking_id logically reference Oracle records.

---

### complaints_feedback

Stores customer complaints and feedback.

Important fields include:

- complaint_id
- customer_id
- vehicle_id
- type
- message
- status
- submitted_at
- rating

The customer_id and vehicle_id logically reference Oracle CUSTOMER and VEHICLE records.

---

### diagnostic_summaries

Stores vehicle diagnostic information.

Important fields include:

- diagnostic_id
- vehicle_id
- job_card_id
- diagnostic_date
- mileage
- battery_voltage
- engine_temperature
- error_codes
- summary

The vehicle_id logically references the Oracle VEHICLE table.

The job_card_id links the diagnostic record to a MongoDB job card.

## 4. Validation Rules

MongoDB JSON Schema validation is applied to all collections.

Validation is used to control:

- Required fields
- BSON data types
- Allowed status values
- Valid complaint or feedback types
- Valid rating ranges
- Non-negative mileage values
- Date fields

Validation helps prevent invalid documents from being inserted into the database.

## 5. MongoDB Indexes

Indexes are used to improve search performance and prevent duplicate logical IDs.

Unique indexes are created for:

- job_card_id
- note_id
- history_id
- complaint_id
- diagnostic_id

Additional indexes are created on commonly searched fields such as:

- vehicle_id
- booking_id
- technician_id
- customer_id
- status
- type
- service_date
- diagnostic_date

## 6. Oracle and MongoDB Integration

Oracle and MongoDB are used together as a hybrid database solution.

Oracle stores structured and transactional data.

MongoDB stores flexible operational information.

MongoDB does not use Oracle foreign-key constraints.

Instead, common identifiers are stored in MongoDB documents and are validated logically by the Spring Boot application.

Example:

CUSTOMER 61
    ↓
VEHICLE 61
    ↓
SERVICE_BOOKING 41
    ↓
JOB_CARD JC001
    ↓
TECHNICIAN 41

This allows data from the relational and NoSQL databases to work together inside the same AutoCare application.

## 7. Application Integration

Spring Boot connects to MongoDB using Spring Data MongoDB.

MongoRepository interfaces are used to perform CRUD operations on MongoDB collections.

MongoDB forms the flexible NoSQL component of the AutoCare hybrid database architecture.