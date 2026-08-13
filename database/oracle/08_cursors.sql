-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 08 - PL/SQL Cursors
-- =========================================================

SET SERVEROUTPUT ON;


-- ---------------------------------------------------------
-- 1. CURSOR - DISPLAY PENDING / CONFIRMED BOOKINGS
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE SHOW_PENDING_BOOKINGS
AS

    CURSOR c_pending_bookings IS
        SELECT
            b.booking_id,
            v.registration_no,
            c.first_name || ' ' || c.last_name AS customer_name,
            b.service_date,
            b.service_type,
            b.booking_status
        FROM SERVICE_BOOKING b
        JOIN VEHICLE v
            ON b.vehicle_id = v.vehicle_id
        JOIN CUSTOMER c
            ON v.customer_id = c.customer_id
        WHERE b.booking_status IN ('Pending', 'Confirmed')
        ORDER BY b.service_date;

BEGIN

    DBMS_OUTPUT.PUT_LINE('===== PENDING / CONFIRMED BOOKINGS =====');

    FOR rec IN c_pending_bookings LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Booking ID: ' || rec.booking_id ||
            ' | Vehicle: ' || rec.registration_no ||
            ' | Customer: ' || rec.customer_name ||
            ' | Service: ' || rec.service_type ||
            ' | Status: ' || rec.booking_status
        );

    END LOOP;

END;
/

-- ---------------------------------------------------------
-- 2. CURSOR - DISPLAY LOW STOCK SPARE PARTS
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE SHOW_LOW_STOCK_PARTS
AS

    CURSOR c_low_stock IS
        SELECT
            part_id,
            part_name,
            quantity_in_stock,
            reorder_level
        FROM SPARE_PART
        WHERE quantity_in_stock <= reorder_level
        ORDER BY quantity_in_stock;

BEGIN

    DBMS_OUTPUT.PUT_LINE('===== LOW STOCK SPARE PARTS =====');

    FOR rec IN c_low_stock LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Part ID: ' || rec.part_id ||
            ' | Part: ' || rec.part_name ||
            ' | Stock: ' || rec.quantity_in_stock ||
            ' | Reorder Level: ' || rec.reorder_level
        );

    END LOOP;

END;
/

-- ---------------------------------------------------------
-- 3. CURSOR - DISPLAY UNPAID / PARTIALLY PAID INVOICES
-- ---------------------------------------------------------

CREATE OR REPLACE PROCEDURE SHOW_UNPAID_INVOICES
AS

    CURSOR c_unpaid_invoices IS
        SELECT
            i.invoice_id,
            v.registration_no,
            i.total_amount,
            i.invoice_status
        FROM INVOICE i
        JOIN SERVICE_BOOKING b
            ON i.booking_id = b.booking_id
        JOIN VEHICLE v
            ON b.vehicle_id = v.vehicle_id
        WHERE i.invoice_status IN ('Unpaid', 'Partially Paid')
        ORDER BY i.invoice_date;

BEGIN

    DBMS_OUTPUT.PUT_LINE('===== UNPAID INVOICES =====');

    FOR rec IN c_unpaid_invoices LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Invoice ID: ' || rec.invoice_id ||
            ' | Vehicle: ' || rec.registration_no ||
            ' | Total: Rs. ' || rec.total_amount ||
            ' | Status: ' || rec.invoice_status
        );

    END LOOP;

END;
/

