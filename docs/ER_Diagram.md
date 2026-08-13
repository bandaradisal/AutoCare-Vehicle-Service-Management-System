# AutoCare Vehicle Service & Maintenance Management System

## Oracle Relational Database ER Diagram

```mermaid
erDiagram

    CUSTOMER ||--o{ VEHICLE : owns
    VEHICLE ||--o{ SERVICE_BOOKING : has
    SERVICE_BOOKING ||--o| INVOICE : generates
    INVOICE ||--o{ INVOICE_ITEM : contains
    SPARE_PART ||--o{ INVOICE_ITEM : used_in
    INVOICE ||--o{ PAYMENT : receives

    CUSTOMER {
        NUMBER customer_id PK
        VARCHAR2 first_name
        VARCHAR2 last_name
        VARCHAR2 nic UK
        VARCHAR2 phone
        VARCHAR2 email UK
        VARCHAR2 address
        DATE created_date
    }

    VEHICLE {
        NUMBER vehicle_id PK
        NUMBER customer_id FK
        VARCHAR2 registration_no UK
        VARCHAR2 brand
        VARCHAR2 model
        NUMBER manufacture_year
        VARCHAR2 fuel_type
        NUMBER current_mileage
        DATE created_date
    }

    SERVICE_BOOKING {
        NUMBER booking_id PK
        NUMBER vehicle_id FK
        DATE booking_date
        DATE service_date
        VARCHAR2 service_type
        VARCHAR2 description
        NUMBER estimated_cost
        VARCHAR2 booking_status
    }

    TECHNICIAN {
        NUMBER technician_id PK
        VARCHAR2 first_name
        VARCHAR2 last_name
        VARCHAR2 phone
        VARCHAR2 email UK
        VARCHAR2 specialization
        NUMBER experience_years
        VARCHAR2 status
        DATE created_date
    }

    SPARE_PART {
        NUMBER part_id PK
        VARCHAR2 part_name
        VARCHAR2 part_category
        NUMBER unit_price
        NUMBER quantity_in_stock
        NUMBER reorder_level
        VARCHAR2 supplier_name
        DATE created_date
    }

    INVOICE {
        NUMBER invoice_id PK
        NUMBER booking_id FK
        DATE invoice_date
        NUMBER service_charge
        NUMBER parts_total
        NUMBER tax_amount
        NUMBER discount
        NUMBER total_amount
        VARCHAR2 invoice_status
    }

    INVOICE_ITEM {
        NUMBER invoice_item_id PK
        NUMBER invoice_id FK
        NUMBER part_id FK
        NUMBER quantity
        NUMBER unit_price
        NUMBER subtotal
    }

    PAYMENT {
        NUMBER payment_id PK
        NUMBER invoice_id FK
        DATE payment_date
        NUMBER amount
        VARCHAR2 payment_method
        VARCHAR2 payment_status
        VARCHAR2 reference_no
    }

    APP_USER {
        NUMBER user_id PK
        VARCHAR2 username UK
        VARCHAR2 email UK
        VARCHAR2 password_hash
        VARCHAR2 role
        NUMBER enabled
        DATE created_date
    }
```

## Relationships

- One customer can own many vehicles.
- One vehicle can have many service bookings.
- One service booking can have zero or one invoice.
- One invoice can contain many invoice items.
- One spare part can appear in many invoice items.
- One invoice can have many payments.
- `TECHNICIAN` is referenced logically by MongoDB job-card documents.
- `APP_USER` is used for authentication and role-based access control.