-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 03 - Additional Constraints and Indexes
-- =========================================================


-- ---------------------------------------------------------
-- 1. EXTRA VEHICLE CONSTRAINTS
-- ---------------------------------------------------------

-- Mileage cannot be negative
ALTER TABLE VEHICLE
ADD CONSTRAINT chk_vehicle_mileage
CHECK (current_mileage >= 0);

-- Manufacture year must be within a reasonable range
ALTER TABLE VEHICLE
ADD CONSTRAINT chk_vehicle_year
CHECK (manufacture_year BETWEEN 1950 AND 2100);


-- ---------------------------------------------------------
-- 2. EXTRA SPARE PART CONSTRAINT
-- ---------------------------------------------------------

-- Spare part name should not be empty
ALTER TABLE SPARE_PART
MODIFY part_name NOT NULL;


-- =========================================================
-- INDEXES
-- =========================================================

-- Helps find all vehicles owned by a customer
CREATE INDEX idx_vehicle_customer
ON VEHICLE(customer_id);


-- Helps find bookings belonging to a vehicle
CREATE INDEX idx_booking_vehicle
ON SERVICE_BOOKING(vehicle_id);


-- Helps search bookings by service date
CREATE INDEX idx_booking_service_date
ON SERVICE_BOOKING(service_date);


-- Helps find invoice items belonging to an invoice
CREATE INDEX idx_invoice_item_invoice
ON INVOICE_ITEM(invoice_id);


-- Helps find invoice items using a particular spare part
CREATE INDEX idx_invoice_item_part
ON INVOICE_ITEM(part_id);


-- Helps find payments belonging to an invoice
CREATE INDEX idx_payment_invoice
ON PAYMENT(invoice_id);


-- Helps search spare parts by name
CREATE INDEX idx_spare_part_name
ON SPARE_PART(part_name);


-- =========================================================
-- End of Script 03
-- =========================================================