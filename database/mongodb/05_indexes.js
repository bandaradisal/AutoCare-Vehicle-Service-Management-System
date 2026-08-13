// =========================================================
// AutoCare Vehicle Service & Maintenance Management System
// MongoDB Script 05 - Indexes
// =========================================================

use("autocare_mongodb");

// =========================================================
// JOB CARDS
// =========================================================

// Unique business ID
db.job_cards.createIndex(
    { job_card_id: 1 },
    {
        unique: true,
        name: "uq_job_cards_job_card_id"
    }
);

// Frequently searched relationships
db.job_cards.createIndex(
    { booking_id: 1 },
    { name: "idx_job_cards_booking_id" }
);

db.job_cards.createIndex(
    { vehicle_id: 1 },
    { name: "idx_job_cards_vehicle_id" }
);

db.job_cards.createIndex(
    { technician_id: 1 },
    { name: "idx_job_cards_technician_id" }
);

db.job_cards.createIndex(
    { status: 1 },
    { name: "idx_job_cards_status" }
);


// =========================================================
// TECHNICIAN NOTES
// =========================================================

db.technician_notes.createIndex(
    { note_id: 1 },
    {
        unique: true,
        name: "uq_technician_notes_note_id"
    }
);

db.technician_notes.createIndex(
    { job_card_id: 1 },
    { name: "idx_technician_notes_job_card_id" }
);

db.technician_notes.createIndex(
    { technician_id: 1 },
    { name: "idx_technician_notes_technician_id" }
);


// =========================================================
// SERVICE HISTORY
// =========================================================

db.service_history.createIndex(
    { history_id: 1 },
    {
        unique: true,
        name: "uq_service_history_history_id"
    }
);

db.service_history.createIndex(
    { vehicle_id: 1 },
    { name: "idx_service_history_vehicle_id" }
);

db.service_history.createIndex(
    { booking_id: 1 },
    { name: "idx_service_history_booking_id" }
);

db.service_history.createIndex(
    { service_date: -1 },
    { name: "idx_service_history_service_date" }
);


// =========================================================
// COMPLAINTS & FEEDBACK
// =========================================================

db.complaints_feedback.createIndex(
    { complaint_id: 1 },
    {
        unique: true,
        name: "uq_complaints_feedback_complaint_id"
    }
);

db.complaints_feedback.createIndex(
    { customer_id: 1 },
    { name: "idx_complaints_customer_id" }
);

db.complaints_feedback.createIndex(
    { vehicle_id: 1 },
    { name: "idx_complaints_vehicle_id" }
);

db.complaints_feedback.createIndex(
    { status: 1 },
    { name: "idx_complaints_status" }
);

db.complaints_feedback.createIndex(
    { type: 1 },
    { name: "idx_complaints_type" }
);


// =========================================================
// DIAGNOSTIC SUMMARIES
// =========================================================

db.diagnostic_summaries.createIndex(
    { diagnostic_id: 1 },
    {
        unique: true,
        name: "uq_diagnostics_diagnostic_id"
    }
);

db.diagnostic_summaries.createIndex(
    { vehicle_id: 1 },
    { name: "idx_diagnostics_vehicle_id" }
);

db.diagnostic_summaries.createIndex(
    { job_card_id: 1 },
    { name: "idx_diagnostics_job_card_id" }
);

db.diagnostic_summaries.createIndex(
    { diagnostic_date: -1 },
    { name: "idx_diagnostics_date" }
);


// =========================================================
// VERIFICATION
// =========================================================

print("===== JOB CARD INDEXES =====");
printjson(db.job_cards.getIndexes());

print("===== TECHNICIAN NOTE INDEXES =====");
printjson(db.technician_notes.getIndexes());

print("===== SERVICE HISTORY INDEXES =====");
printjson(db.service_history.getIndexes());

print("===== COMPLAINT / FEEDBACK INDEXES =====");
printjson(db.complaints_feedback.getIndexes());

print("===== DIAGNOSTIC INDEXES =====");
printjson(db.diagnostic_summaries.getIndexes());

print("MongoDB index script completed successfully.");