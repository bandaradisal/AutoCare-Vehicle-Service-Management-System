-- =========================================================
-- AutoCare Vehicle Service & Maintenance Management System
-- Script 01 - Create Project User
-- =========================================================

-- The password is requested when this script is executed.
-- The real password is NOT stored in the project.

CREATE USER AUTOCARE
IDENTIFIED BY "&AUTOCARE_PASSWORD";

GRANT CREATE SESSION TO AUTOCARE;
GRANT CREATE TABLE TO AUTOCARE;
GRANT CREATE VIEW TO AUTOCARE;
GRANT CREATE SEQUENCE TO AUTOCARE;
GRANT CREATE PROCEDURE TO AUTOCARE;
GRANT CREATE TRIGGER TO AUTOCARE;

ALTER USER AUTOCARE
QUOTA UNLIMITED ON USERS;

-- =========================================================
-- End of Script 01
-- =========================================================