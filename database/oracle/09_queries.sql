-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 09 - Standard SQL Queries
-- =========================================================


-- ---------------------------------------------------------
-- 1. VIEW ALL CUSTOMERS
-- ---------------------------------------------------------

SELECT
    customer_id,
    first_name,
    last_name,
    nic,
    phone,
    email,
    address,
    created_date
FROM CUSTOMER
ORDER BY customer_id;


-- ---------------------------------------------------------
-- 2. VIEW CUSTOMERS AND THEIR VEHICLES
-- ---------------------------------------------------------

SELECT
    c.customer_id,
    c.first_name || ' ' || c.last_name AS customer_name,
    c.phone,
    v.vehicle_id,
    v.registration_no,
    v.brand,
    v.model,
    v.manufacture_year,
    v.fuel_type,
    v.current_mileage
FROM CUSTOMER c
JOIN VEHICLE v
    ON c.customer_id = v.customer_id
ORDER BY c.customer_id;


-- ---------------------------------------------------------
-- 3. SEARCH VEHICLE BY REGISTRATION NUMBER
-- ---------------------------------------------------------

SELECT
    v.vehicle_id,
    v.registration_no,
    v.brand,
    v.model,
    v.manufacture_year,
    v.current_mileage,
    c.first_name || ' ' || c.last_name AS owner_name,
    c.phone
FROM VEHICLE v
JOIN CUSTOMER c
    ON v.customer_id = c.customer_id
WHERE v.registration_no = 'CAA-4587';


-- ---------------------------------------------------------
-- 4. VIEW ALL SERVICE BOOKINGS
-- ---------------------------------------------------------

SELECT
    b.booking_id,
    v.registration_no,
    v.brand,
    v.model,
    c.first_name || ' ' || c.last_name AS customer_name,
    b.service_date,
    b.service_type,
    b.estimated_cost,
    b.booking_status
FROM SERVICE_BOOKING b
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
JOIN CUSTOMER c
    ON v.customer_id = c.customer_id
ORDER BY b.service_date;


-- ---------------------------------------------------------
-- 5. VIEW PENDING AND CONFIRMED BOOKINGS
-- ---------------------------------------------------------

SELECT
    b.booking_id,
    v.registration_no,
    b.service_date,
    b.service_type,
    b.booking_status
FROM SERVICE_BOOKING b
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
WHERE b.booking_status IN ('Pending', 'Confirmed')
ORDER BY b.service_date;


-- ---------------------------------------------------------
-- 6. VIEW AVAILABLE TECHNICIANS
-- ---------------------------------------------------------

SELECT
    technician_id,
    first_name,
    last_name,
    phone,
    specialization,
    experience_years,
    status
FROM TECHNICIAN
WHERE status = 'Available'
ORDER BY first_name;


-- ---------------------------------------------------------
-- 7. VIEW SPARE PART INVENTORY
-- ---------------------------------------------------------

SELECT
    part_id,
    part_name,
    part_category,
    unit_price,
    quantity_in_stock,
    reorder_level,
    supplier_name
FROM SPARE_PART
ORDER BY part_name;


-- ---------------------------------------------------------
-- 8. VIEW LOW STOCK SPARE PARTS
-- ---------------------------------------------------------

SELECT
    part_id,
    part_name,
    quantity_in_stock,
    reorder_level,
    supplier_name
FROM SPARE_PART
WHERE quantity_in_stock <= reorder_level
ORDER BY quantity_in_stock;


-- ---------------------------------------------------------
-- 9. VIEW INVOICE DETAILS
-- ---------------------------------------------------------

SELECT
    i.invoice_id,
    v.registration_no,
    c.first_name || ' ' || c.last_name AS customer_name,
    b.service_type,
    i.invoice_date,
    i.service_charge,
    i.parts_total,
    i.tax_amount,
    i.discount,
    i.total_amount,
    i.invoice_status
FROM INVOICE i
JOIN SERVICE_BOOKING b
    ON i.booking_id = b.booking_id
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
JOIN CUSTOMER c
    ON v.customer_id = c.customer_id
ORDER BY i.invoice_date DESC;


-- ---------------------------------------------------------
-- 10. VIEW SPARE PARTS USED IN INVOICES
-- ---------------------------------------------------------

SELECT
    i.invoice_id,
    sp.part_name,
    ii.quantity,
    ii.unit_price,
    ii.subtotal
FROM INVOICE_ITEM ii
JOIN INVOICE i
    ON ii.invoice_id = i.invoice_id
JOIN SPARE_PART sp
    ON ii.part_id = sp.part_id
ORDER BY i.invoice_id;


-- ---------------------------------------------------------
-- 11. VIEW PAYMENT HISTORY
-- ---------------------------------------------------------

SELECT
    p.payment_id,
    p.invoice_id,
    p.payment_date,
    p.amount,
    p.payment_method,
    p.payment_status,
    p.reference_no
FROM PAYMENT p
ORDER BY p.payment_date DESC;


-- ---------------------------------------------------------
-- 12. VIEW INVOICE AND PAYMENT SUMMARY
-- ---------------------------------------------------------

SELECT
    i.invoice_id,
    i.total_amount,
    NVL(SUM(
        CASE
            WHEN p.payment_status = 'Completed'
            THEN p.amount
            ELSE 0
        END
    ), 0) AS amount_paid,

    i.total_amount -
    NVL(SUM(
        CASE
            WHEN p.payment_status = 'Completed'
            THEN p.amount
            ELSE 0
        END
    ), 0) AS balance,

    i.invoice_status
FROM INVOICE i
LEFT JOIN PAYMENT p
    ON i.invoice_id = p.invoice_id
GROUP BY
    i.invoice_id,
    i.total_amount,
    i.invoice_status
ORDER BY i.invoice_id;


-- ---------------------------------------------------------
-- 13. COUNT VEHICLES OWNED BY EACH CUSTOMER
-- ---------------------------------------------------------

SELECT
    c.customer_id,
    c.first_name || ' ' || c.last_name AS customer_name,
    COUNT(v.vehicle_id) AS number_of_vehicles
FROM CUSTOMER c
LEFT JOIN VEHICLE v
    ON c.customer_id = v.customer_id
GROUP BY
    c.customer_id,
    c.first_name,
    c.last_name
ORDER BY c.customer_id;


-- ---------------------------------------------------------
-- 14. COUNT BOOKINGS BY STATUS
-- ---------------------------------------------------------

SELECT
    booking_status,
    COUNT(*) AS number_of_bookings
FROM SERVICE_BOOKING
GROUP BY booking_status
ORDER BY booking_status;


-- ---------------------------------------------------------
-- 15. VIEW COMPLETE SERVICE BOOKING INFORMATION
-- ---------------------------------------------------------

SELECT
    b.booking_id,
    c.first_name || ' ' || c.last_name AS customer_name,
    c.phone,
    v.registration_no,
    v.brand || ' ' || v.model AS vehicle,
    b.service_date,
    b.service_type,
    b.description,
    b.estimated_cost,
    b.booking_status
FROM SERVICE_BOOKING b
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
JOIN CUSTOMER c
    ON v.customer_id = c.customer_id
ORDER BY b.service_date;


-- =========================================================
-- End of Script 09
-- =========================================================