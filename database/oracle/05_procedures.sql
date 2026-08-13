-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 05 - PL/SQL Procedures
-- =========================================================


-- ---------------------------------------------------------
-- 1. ADD CUSTOMER
-- Creates a new customer
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE ADD_CUSTOMER (
    p_first_name IN VARCHAR2,
    p_last_name  IN VARCHAR2,
    p_nic        IN VARCHAR2,
    p_phone      IN VARCHAR2,
    p_email      IN VARCHAR2,
    p_address    IN VARCHAR2
)
AS
BEGIN

    INSERT INTO CUSTOMER (
        first_name,
        last_name,
        nic,
        phone,
        email,
        address
    )
    VALUES (
        p_first_name,
        p_last_name,
        p_nic,
        p_phone,
        p_email,
        p_address
    );

END;
/

-- ---------------------------------------------------------
-- 2. UPDATE CUSTOMER
-- Updates customer contact information
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE UPDATE_CUSTOMER (
    p_customer_id IN NUMBER,
    p_phone       IN VARCHAR2,
    p_email       IN VARCHAR2,
    p_address     IN VARCHAR2
)
AS
BEGIN

    UPDATE CUSTOMER
    SET phone   = p_phone,
        email   = p_email,
        address = p_address
    WHERE customer_id = p_customer_id;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20001,
            'Customer not found.'
        );
    END IF;

END;
/

-- ---------------------------------------------------------
-- 3. DELETE CUSTOMER
-- Deletes customer only when no vehicles are registered
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE DELETE_CUSTOMER (
    p_customer_id IN NUMBER
)
AS
    v_vehicle_count NUMBER;
BEGIN

    SELECT COUNT(*)
    INTO v_vehicle_count
    FROM VEHICLE
    WHERE customer_id = p_customer_id;

    IF v_vehicle_count > 0 THEN

        RAISE_APPLICATION_ERROR(
            -20002,
            'Cannot delete customer because vehicles are registered.'
        );

    ELSE

        DELETE FROM CUSTOMER
        WHERE customer_id = p_customer_id;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(
                -20003,
                'Customer not found.'
            );
        END IF;

    END IF;

END;
/

-- ---------------------------------------------------------
-- 4. ADD VEHICLE
-- Registers a vehicle for an existing customer
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE ADD_VEHICLE (
    p_customer_id      IN NUMBER,
    p_registration_no  IN VARCHAR2,
    p_brand            IN VARCHAR2,
    p_model            IN VARCHAR2,
    p_manufacture_year IN NUMBER,
    p_fuel_type        IN VARCHAR2,
    p_current_mileage  IN NUMBER
)
AS
BEGIN

    INSERT INTO VEHICLE (
        customer_id,
        registration_no,
        brand,
        model,
        manufacture_year,
        fuel_type,
        current_mileage
    )
    VALUES (
        p_customer_id,
        p_registration_no,
        p_brand,
        p_model,
        p_manufacture_year,
        p_fuel_type,
        p_current_mileage
    );

END;
/

-- ---------------------------------------------------------
-- 5. CREATE SERVICE BOOKING
-- Creates a booking for a registered vehicle
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE CREATE_SERVICE_BOOKING (
    p_vehicle_id     IN NUMBER,
    p_service_date   IN DATE,
    p_service_type   IN VARCHAR2,
    p_description    IN VARCHAR2,
    p_estimated_cost IN NUMBER
)
AS
BEGIN

    INSERT INTO SERVICE_BOOKING (
        vehicle_id,
        service_date,
        service_type,
        description,
        estimated_cost,
        booking_status
    )
    VALUES (
        p_vehicle_id,
        p_service_date,
        p_service_type,
        p_description,
        p_estimated_cost,
        'Pending'
    );

END;
/

-- ---------------------------------------------------------
-- 6. UPDATE BOOKING STATUS
-- Changes service booking status
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE UPDATE_BOOKING_STATUS (
    p_booking_id IN NUMBER,
    p_status     IN VARCHAR2
)
AS
BEGIN

    IF p_status NOT IN (
        'Pending',
        'Confirmed',
        'In Progress',
        'Completed',
        'Cancelled'
    ) THEN

        RAISE_APPLICATION_ERROR(
            -20004,
            'Invalid booking status.'
        );

    END IF;

    UPDATE SERVICE_BOOKING
    SET booking_status = p_status
    WHERE booking_id = p_booking_id;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20005,
            'Booking not found.'
        );
    END IF;

END;
/

-- ---------------------------------------------------------
-- 7. UPDATE SPARE PART STOCK
-- Changes quantity of an inventory item
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE UPDATE_SPARE_STOCK (
    p_part_id      IN NUMBER,
    p_new_quantity IN NUMBER
)
AS
BEGIN

    IF p_new_quantity < 0 THEN
        RAISE_APPLICATION_ERROR(
            -20006,
            'Stock quantity cannot be negative.'
        );
    END IF;

    UPDATE SPARE_PART
    SET quantity_in_stock = p_new_quantity
    WHERE part_id = p_part_id;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20007,
            'Spare part not found.'
        );
    END IF;

END;
/

-- ---------------------------------------------------------
-- 8. RECORD PAYMENT
-- Records a payment against an invoice
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE RECORD_PAYMENT (
    p_invoice_id     IN NUMBER,
    p_amount         IN NUMBER,
    p_payment_method IN VARCHAR2,
    p_reference_no   IN VARCHAR2
)
AS
BEGIN

    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(
            -20008,
            'Payment amount must be greater than zero.'
        );
    END IF;

    INSERT INTO PAYMENT (
        invoice_id,
        amount,
        payment_method,
        payment_status,
        reference_no
    )
    VALUES (
        p_invoice_id,
        p_amount,
        p_payment_method,
        'Completed',
        p_reference_no
    );

END;
/

