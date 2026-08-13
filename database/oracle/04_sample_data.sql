-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 04 - Sample Data
--
-- SAFE / IDEMPOTENT VERSION
-- This script can be executed multiple times.
-- Existing sample records will not be inserted again.
-- =========================================================


-- =========================================================
-- 1. CUSTOMER SAMPLE DATA
-- =========================================================


-- Customer 1 - Amal Fernando

INSERT INTO CUSTOMER (
    first_name,
    last_name,
    nic,
    phone,
    email,
    address
)
SELECT
    'Amal',
    'Fernando',
    '199812345678',
    '0711111111',
    'amal.fernando@gmail.com',
    'Colombo'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM CUSTOMER
    WHERE nic = '199812345678'
       OR email = 'amal.fernando@gmail.com'
);


-- Customer 2 - Kavindi Perera

INSERT INTO CUSTOMER (
    first_name,
    last_name,
    nic,
    phone,
    email,
    address
)
SELECT
    'Kavindi',
    'Perera',
    '200156789012',
    '0722222222',
    'kavindi.perera@gmail.com',
    'Kandy'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM CUSTOMER
    WHERE nic = '200156789012'
       OR email = 'kavindi.perera@gmail.com'
);


-- Customer 3 - Sahan Jayasinghe

INSERT INTO CUSTOMER (
    first_name,
    last_name,
    nic,
    phone,
    email,
    address
)
SELECT
    'Sahan',
    'Jayasinghe',
    '199745678901',
    '0753333333',
    'sahan.jayasinghe@gmail.com',
    'Kurunegala'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM CUSTOMER
    WHERE nic = '199745678901'
       OR email = 'sahan.jayasinghe@gmail.com'
);


-- Customer 4 - Dinuka Bandara

INSERT INTO CUSTOMER (
    first_name,
    last_name,
    nic,
    phone,
    email,
    address
)
SELECT
    'Dinuka',
    'Bandara',
    '199934567890',
    '0764444444',
    'dinuka.bandara@gmail.com',
    'Matale'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM CUSTOMER
    WHERE nic = '199934567890'
       OR email = 'dinuka.bandara@gmail.com'
);


-- Customer 5 - Tharushi Silva

INSERT INTO CUSTOMER (
    first_name,
    last_name,
    nic,
    phone,
    email,
    address
)
SELECT
    'Tharushi',
    'Silva',
    '200278901234',
    '0775555555',
    'tharushi.silva@gmail.com',
    'Gampaha'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM CUSTOMER
    WHERE nic = '200278901234'
       OR email = 'tharushi.silva@gmail.com'
);



-- =========================================================
-- 2. VEHICLE SAMPLE DATA
-- =========================================================


-- Vehicle 1 - Toyota Prius

INSERT INTO VEHICLE (
    customer_id,
    registration_no,
    brand,
    model,
    manufacture_year,
    fuel_type,
    current_mileage
)
SELECT
    c.customer_id,
    'CAA-4587',
    'Toyota',
    'Prius',
    2017,
    'Hybrid',
    78500
FROM CUSTOMER c
WHERE c.email = 'amal.fernando@gmail.com'
AND NOT EXISTS (
    SELECT 1
    FROM VEHICLE v
    WHERE v.registration_no = 'CAA-4587'
);


-- Vehicle 2 - Honda Vezel

INSERT INTO VEHICLE (
    customer_id,
    registration_no,
    brand,
    model,
    manufacture_year,
    fuel_type,
    current_mileage
)
SELECT
    c.customer_id,
    'CBF-7821',
    'Honda',
    'Vezel',
    2019,
    'Hybrid',
    49000
FROM CUSTOMER c
WHERE c.email = 'kavindi.perera@gmail.com'
AND NOT EXISTS (
    SELECT 1
    FROM VEHICLE v
    WHERE v.registration_no = 'CBF-7821'
);


-- Vehicle 3 - Suzuki Wagon R

INSERT INTO VEHICLE (
    customer_id,
    registration_no,
    brand,
    model,
    manufacture_year,
    fuel_type,
    current_mileage
)
SELECT
    c.customer_id,
    'CAD-3256',
    'Suzuki',
    'Wagon R',
    2018,
    'Petrol',
    62000
FROM CUSTOMER c
WHERE c.email = 'sahan.jayasinghe@gmail.com'
AND NOT EXISTS (
    SELECT 1
    FROM VEHICLE v
    WHERE v.registration_no = 'CAD-3256'
);


-- Vehicle 4 - Nissan X-Trail

INSERT INTO VEHICLE (
    customer_id,
    registration_no,
    brand,
    model,
    manufacture_year,
    fuel_type,
    current_mileage
)
SELECT
    c.customer_id,
    'CBB-9145',
    'Nissan',
    'X-Trail',
    2016,
    'Petrol',
    91000
FROM CUSTOMER c
WHERE c.email = 'dinuka.bandara@gmail.com'
AND NOT EXISTS (
    SELECT 1
    FROM VEHICLE v
    WHERE v.registration_no = 'CBB-9145'
);


-- Vehicle 5 - Mazda Axela

INSERT INTO VEHICLE (
    customer_id,
    registration_no,
    brand,
    model,
    manufacture_year,
    fuel_type,
    current_mileage
)
SELECT
    c.customer_id,
    'CAR-6742',
    'Mazda',
    'Axela',
    2020,
    'Petrol',
    36500
FROM CUSTOMER c
WHERE c.email = 'tharushi.silva@gmail.com'
AND NOT EXISTS (
    SELECT 1
    FROM VEHICLE v
    WHERE v.registration_no = 'CAR-6742'
);



-- =========================================================
-- 3. SERVICE BOOKING SAMPLE DATA
-- =========================================================


-- Toyota Prius - Full Service

INSERT INTO SERVICE_BOOKING (
    vehicle_id,
    service_date,
    service_type,
    description,
    estimated_cost,
    booking_status
)
SELECT
    v.vehicle_id,
    DATE '2026-08-15',
    'Full Service',
    'Routine full vehicle maintenance',
    28000,
    'Completed'
FROM VEHICLE v
WHERE v.registration_no = 'CAA-4587'
AND NOT EXISTS (
    SELECT 1
    FROM SERVICE_BOOKING b
    WHERE b.vehicle_id = v.vehicle_id
      AND b.service_date = DATE '2026-08-15'
      AND b.service_type = 'Full Service'
);


-- Honda Vezel - Brake Service

INSERT INTO SERVICE_BOOKING (
    vehicle_id,
    service_date,
    service_type,
    description,
    estimated_cost,
    booking_status
)
SELECT
    v.vehicle_id,
    DATE '2026-08-18',
    'Brake Service',
    'Brake pad inspection and replacement',
    18500,
    'Confirmed'
FROM VEHICLE v
WHERE v.registration_no = 'CBF-7821'
AND NOT EXISTS (
    SELECT 1
    FROM SERVICE_BOOKING b
    WHERE b.vehicle_id = v.vehicle_id
      AND b.service_date = DATE '2026-08-18'
      AND b.service_type = 'Brake Service'
);


-- Suzuki Wagon R - Oil Change

INSERT INTO SERVICE_BOOKING (
    vehicle_id,
    service_date,
    service_type,
    description,
    estimated_cost,
    booking_status
)
SELECT
    v.vehicle_id,
    DATE '2026-08-20',
    'Oil Change',
    'Engine oil and oil filter replacement',
    12000,
    'Pending'
FROM VEHICLE v
WHERE v.registration_no = 'CAD-3256'
AND NOT EXISTS (
    SELECT 1
    FROM SERVICE_BOOKING b
    WHERE b.vehicle_id = v.vehicle_id
      AND b.service_date = DATE '2026-08-20'
      AND b.service_type = 'Oil Change'
);


-- Nissan X-Trail - Engine Repair

INSERT INTO SERVICE_BOOKING (
    vehicle_id,
    service_date,
    service_type,
    description,
    estimated_cost,
    booking_status
)
SELECT
    v.vehicle_id,
    DATE '2026-08-16',
    'Engine Repair',
    'Engine vibration and noise inspection',
    45000,
    'In Progress'
FROM VEHICLE v
WHERE v.registration_no = 'CBB-9145'
AND NOT EXISTS (
    SELECT 1
    FROM SERVICE_BOOKING b
    WHERE b.vehicle_id = v.vehicle_id
      AND b.service_date = DATE '2026-08-16'
      AND b.service_type = 'Engine Repair'
);


-- Mazda Axela - General Inspection

INSERT INTO SERVICE_BOOKING (
    vehicle_id,
    service_date,
    service_type,
    description,
    estimated_cost,
    booking_status
)
SELECT
    v.vehicle_id,
    DATE '2026-08-22',
    'General Inspection',
    'Complete vehicle safety inspection',
    10000,
    'Confirmed'
FROM VEHICLE v
WHERE v.registration_no = 'CAR-6742'
AND NOT EXISTS (
    SELECT 1
    FROM SERVICE_BOOKING b
    WHERE b.vehicle_id = v.vehicle_id
      AND b.service_date = DATE '2026-08-22'
      AND b.service_type = 'General Inspection'
);



-- =========================================================
-- 4. TECHNICIAN SAMPLE DATA
-- =========================================================


-- Technician 1

INSERT INTO TECHNICIAN (
    first_name,
    last_name,
    phone,
    email,
    specialization,
    experience_years,
    status
)
SELECT
    'Ruwan',
    'Perera',
    '0701112233',
    'ruwan.perera@autocare.lk',
    'Engine Repair',
    8,
    'Assigned'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM TECHNICIAN
    WHERE email = 'ruwan.perera@autocare.lk'
);


-- Technician 2

INSERT INTO TECHNICIAN (
    first_name,
    last_name,
    phone,
    email,
    specialization,
    experience_years,
    status
)
SELECT
    'Chamika',
    'Silva',
    '0702223344',
    'chamika.silva@autocare.lk',
    'Electrical Systems',
    5,
    'Available'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM TECHNICIAN
    WHERE email = 'chamika.silva@autocare.lk'
);


-- Technician 3

INSERT INTO TECHNICIAN (
    first_name,
    last_name,
    phone,
    email,
    specialization,
    experience_years,
    status
)
SELECT
    'Nuwan',
    'Fernando',
    '0703334455',
    'nuwan.fernando@autocare.lk',
    'Brake and Suspension',
    6,
    'Available'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM TECHNICIAN
    WHERE email = 'nuwan.fernando@autocare.lk'
);


-- Technician 4

INSERT INTO TECHNICIAN (
    first_name,
    last_name,
    phone,
    email,
    specialization,
    experience_years,
    status
)
SELECT
    'Isuru',
    'Bandara',
    '0704445566',
    'isuru.bandara@autocare.lk',
    'General Maintenance',
    4,
    'Assigned'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM TECHNICIAN
    WHERE email = 'isuru.bandara@autocare.lk'
);



-- =========================================================
-- 5. SPARE PART SAMPLE DATA
-- =========================================================


-- Synthetic Engine Oil

INSERT INTO SPARE_PART (
    part_name,
    part_category,
    unit_price,
    quantity_in_stock,
    reorder_level,
    supplier_name
)
SELECT
    'Synthetic Engine Oil 4L',
    'Lubricant',
    9500,
    25,
    8,
    'Lanka Auto Supplies'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM SPARE_PART
    WHERE part_name = 'Synthetic Engine Oil 4L'
);


-- Front Brake Pad Set

INSERT INTO SPARE_PART (
    part_name,
    part_category,
    unit_price,
    quantity_in_stock,
    reorder_level,
    supplier_name
)
SELECT
    'Front Brake Pad Set',
    'Brake',
    12500,
    15,
    5,
    'Central Motor Parts'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM SPARE_PART
    WHERE part_name = 'Front Brake Pad Set'
);


-- Spark Plug Set

INSERT INTO SPARE_PART (
    part_name,
    part_category,
    unit_price,
    quantity_in_stock,
    reorder_level,
    supplier_name
)
SELECT
    'Spark Plug Set',
    'Engine',
    7200,
    18,
    6,
    'Auto Parts Lanka'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM SPARE_PART
    WHERE part_name = 'Spark Plug Set'
);


-- Vehicle Battery

INSERT INTO SPARE_PART (
    part_name,
    part_category,
    unit_price,
    quantity_in_stock,
    reorder_level,
    supplier_name
)
SELECT
    '12V Vehicle Battery',
    'Electrical',
    28500,
    8,
    3,
    'Power Battery Lanka'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM SPARE_PART
    WHERE part_name = '12V Vehicle Battery'
);


-- Cabin Air Filter

INSERT INTO SPARE_PART (
    part_name,
    part_category,
    unit_price,
    quantity_in_stock,
    reorder_level,
    supplier_name
)
SELECT
    'Cabin Air Filter',
    'Filter',
    4200,
    20,
    7,
    'Central Motor Parts'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM SPARE_PART
    WHERE part_name = 'Cabin Air Filter'
);



-- =========================================================
-- 6. INVOICE SAMPLE DATA
-- =========================================================

INSERT INTO INVOICE (
    booking_id,
    service_charge,
    parts_total,
    tax_amount,
    discount,
    total_amount,
    invoice_status
)
SELECT
    b.booking_id,
    12000,
    13700,
    2313,
    1000,
    27013,
    'Paid'
FROM SERVICE_BOOKING b
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
WHERE v.registration_no = 'CAA-4587'
  AND b.service_date = DATE '2026-08-15'
  AND b.service_type = 'Full Service'
  AND NOT EXISTS (
      SELECT 1
      FROM INVOICE i
      WHERE i.booking_id = b.booking_id
  );



-- =========================================================
-- 7. INVOICE ITEM SAMPLE DATA
-- =========================================================


-- Synthetic Engine Oil invoice item

INSERT INTO INVOICE_ITEM (
    invoice_id,
    part_id,
    quantity,
    unit_price,
    subtotal
)
SELECT
    i.invoice_id,
    p.part_id,
    1,
    9500,
    9500
FROM INVOICE i
JOIN SERVICE_BOOKING b
    ON i.booking_id = b.booking_id
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
JOIN SPARE_PART p
    ON p.part_name = 'Synthetic Engine Oil 4L'
WHERE v.registration_no = 'CAA-4587'
  AND b.service_date = DATE '2026-08-15'
  AND b.service_type = 'Full Service'
  AND p.part_id = (
      SELECT MIN(sp.part_id)
      FROM SPARE_PART sp
      WHERE sp.part_name = 'Synthetic Engine Oil 4L'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM INVOICE_ITEM ii
      WHERE ii.invoice_id = i.invoice_id
        AND ii.part_id = p.part_id
  );


-- Cabin Air Filter invoice item

INSERT INTO INVOICE_ITEM (
    invoice_id,
    part_id,
    quantity,
    unit_price,
    subtotal
)
SELECT
    i.invoice_id,
    p.part_id,
    1,
    4200,
    4200
FROM INVOICE i
JOIN SERVICE_BOOKING b
    ON i.booking_id = b.booking_id
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
JOIN SPARE_PART p
    ON p.part_name = 'Cabin Air Filter'
WHERE v.registration_no = 'CAA-4587'
  AND b.service_date = DATE '2026-08-15'
  AND b.service_type = 'Full Service'
  AND p.part_id = (
      SELECT MIN(sp.part_id)
      FROM SPARE_PART sp
      WHERE sp.part_name = 'Cabin Air Filter'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM INVOICE_ITEM ii
      WHERE ii.invoice_id = i.invoice_id
        AND ii.part_id = p.part_id
  );



-- =========================================================
-- 8. PAYMENT SAMPLE DATA
-- =========================================================

INSERT INTO PAYMENT (
    invoice_id,
    amount,
    payment_method,
    payment_status,
    reference_no
)
SELECT
    i.invoice_id,
    27013,
    'Card',
    'Completed',
    'CARD-2026-0001'
FROM INVOICE i
JOIN SERVICE_BOOKING b
    ON i.booking_id = b.booking_id
JOIN VEHICLE v
    ON b.vehicle_id = v.vehicle_id
WHERE v.registration_no = 'CAA-4587'
  AND b.service_date = DATE '2026-08-15'
  AND b.service_type = 'Full Service'
  AND NOT EXISTS (
      SELECT 1
      FROM PAYMENT p
      WHERE p.reference_no = 'CARD-2026-0001'
  );



-- =========================================================
-- SAVE SAMPLE DATA
-- =========================================================

COMMIT;


-- =========================================================
-- OPTIONAL VERIFICATION
-- =========================================================

SELECT 'CUSTOMER' AS table_name, COUNT(*) AS total_records
FROM CUSTOMER

UNION ALL

SELECT 'VEHICLE', COUNT(*)
FROM VEHICLE

UNION ALL

SELECT 'SERVICE_BOOKING', COUNT(*)
FROM SERVICE_BOOKING

UNION ALL

SELECT 'TECHNICIAN', COUNT(*)
FROM TECHNICIAN

UNION ALL

SELECT 'SPARE_PART', COUNT(*)
FROM SPARE_PART

UNION ALL

SELECT 'INVOICE', COUNT(*)
FROM INVOICE

UNION ALL

SELECT 'INVOICE_ITEM', COUNT(*)
FROM INVOICE_ITEM

UNION ALL

SELECT 'PAYMENT', COUNT(*)
FROM PAYMENT;


-- =========================================================
-- End of Script 04
-- =========================================================