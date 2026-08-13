-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 06 - PL/SQL Functions
-- =========================================================


-- ---------------------------------------------------------
-- 1. GET CUSTOMER VEHICLE COUNT
-- Returns the number of vehicles owned by a customer
-- ---------------------------------------------------------

CREATE OR REPLACE FUNCTION GET_CUSTOMER_VEHICLE_COUNT (
    p_customer_id IN NUMBER
)
RETURN NUMBER
AS
    v_count NUMBER;
BEGIN

    SELECT COUNT(*)
    INTO v_count
    FROM VEHICLE
    WHERE customer_id = p_customer_id;

    RETURN v_count;

END;
/

-- ---------------------------------------------------------
-- 2. CHECK PART STOCK
-- Returns the current stock quantity of a spare part
-- ---------------------------------------------------------

CREATE OR REPLACE FUNCTION CHECK_PART_STOCK (
    p_part_id IN NUMBER
)
RETURN NUMBER
AS
    v_quantity NUMBER;
BEGIN

    SELECT quantity_in_stock
    INTO v_quantity
    FROM SPARE_PART
    WHERE part_id = p_part_id;

    RETURN v_quantity;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN -1;
END;
/

-- ---------------------------------------------------------
-- 3. CALCULATE INVOICE TOTAL
-- Calculates total invoice amount
-- ---------------------------------------------------------

CREATE OR REPLACE FUNCTION CALCULATE_INVOICE_TOTAL (
    p_service_charge IN NUMBER,
    p_parts_total    IN NUMBER,
    p_tax_amount     IN NUMBER,
    p_discount       IN NUMBER
)
RETURN NUMBER
AS
    v_total NUMBER;
BEGIN

    v_total :=
        NVL(p_service_charge, 0)
        + NVL(p_parts_total, 0)
        + NVL(p_tax_amount, 0)
        - NVL(p_discount, 0);

    RETURN v_total;

END;
/

-- ---------------------------------------------------------
-- 4. GET INVOICE BALANCE
-- Returns the remaining unpaid balance of an invoice
-- ---------------------------------------------------------

CREATE OR REPLACE FUNCTION GET_INVOICE_BALANCE (
    p_invoice_id IN NUMBER
)
RETURN NUMBER
AS
    v_total_amount NUMBER;
    v_paid_amount  NUMBER;
BEGIN

    SELECT total_amount
    INTO v_total_amount
    FROM INVOICE
    WHERE invoice_id = p_invoice_id;

    SELECT NVL(SUM(amount), 0)
    INTO v_paid_amount
    FROM PAYMENT
    WHERE invoice_id = p_invoice_id
      AND payment_status = 'Completed';

    RETURN v_total_amount - v_paid_amount;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN -1;
END;
/

