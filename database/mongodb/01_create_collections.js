// =========================================================
// AutoCare Vehicle Service & Maintenance Management System
// MongoDB Script 01 - Create Collections
// =========================================================

// Select / create the AutoCare MongoDB database
use("autocare_mongodb");

// ---------------------------------------------------------
// 1. Vehicle Job Cards
// ---------------------------------------------------------
db.createCollection("job_cards");

// ---------------------------------------------------------
// 2. Technician Notes
// ---------------------------------------------------------
db.createCollection("technician_notes");

// ---------------------------------------------------------
// 3. Vehicle Service History
// ---------------------------------------------------------
db.createCollection("service_history");

// ---------------------------------------------------------
// 4. Customer Complaints and Feedback
// ---------------------------------------------------------
db.createCollection("complaints_feedback");

// ---------------------------------------------------------
// 5. Diagnostic Data Summaries
// ---------------------------------------------------------
db.createCollection("diagnostic_summaries");