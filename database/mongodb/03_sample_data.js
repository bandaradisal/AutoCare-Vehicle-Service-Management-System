// =========================================================
// AutoCare Vehicle Service & Maintenance Management System
// Script 03 - MongoDB Sample Data
//
// SAFE / IDEMPOTENT VERSION
// This script can be executed multiple times.
// Existing sample documents will not be duplicated.
// =========================================================


db = db.getSiblingDB("autocare_mongodb");


// =========================================================
// 1. JOB CARD
// =========================================================

db.job_cards.updateOne(
    {
        job_card_id: "JC001"
    },

    {
        $setOnInsert: {

            job_card_id: "JC001",

            booking_id: NumberInt(41),

            vehicle_id: NumberInt(61),

            technician_id: NumberInt(41),

            opened_date: ISODate("2026-08-15T08:30:00Z"),

            status: "Completed",

            tasks: [
                {
                    description: "Inspect engine oil",
                    status: "Completed"
                },
                {
                    description: "Replace engine oil",
                    status: "Completed"
                },
                {
                    description: "Replace cabin air filter",
                    status: "Completed"
                }
            ]
        }
    },

    {
        upsert: true
    }
);


// =========================================================
// 2. TECHNICIAN NOTES
// =========================================================

db.technician_notes.updateOne(
    {
        note_id: "TN001"
    },

    {
        $setOnInsert: {

            note_id: "TN001",

            job_card_id: "JC001",

            technician_id: NumberInt(41),

            created_at: ISODate("2026-08-15T10:15:00Z"),

            notes: [
                "Engine oil was dark and required replacement.",
                "Cabin air filter contained excessive dust.",
                "Brake system inspected and found to be in good condition."
            ]
        }
    },

    {
        upsert: true
    }
);


// =========================================================
// 3. SERVICE HISTORY
// =========================================================

db.service_history.updateOne(
    {
        history_id: "SH001"
    },

    {
        $setOnInsert: {

            history_id: "SH001",

            vehicle_id: NumberInt(61),

            booking_id: NumberInt(41),

            service_date: ISODate("2026-08-15T00:00:00Z"),

            service_type: "Full Service",

            mileage: NumberInt(78500),

            summary: "Routine full service completed successfully.",

            parts_used: [
                "Synthetic Engine Oil 4L",
                "Cabin Air Filter"
            ]
        }
    },

    {
        upsert: true
    }
);


// =========================================================
// 4. COMPLAINT / FEEDBACK
// =========================================================


// Feedback - CMP001

db.complaints_feedback.updateOne(
    {
        complaint_id: "CMP001"
    },

    {
        $setOnInsert: {

            complaint_id: "CMP001",

            customer_id: NumberInt(61),

            vehicle_id: NumberInt(61),

            type: "Feedback",

            message:
                "The staff were helpful and the vehicle service was completed properly.",

            rating: NumberInt(5),

            status: "Closed",

            submitted_at: ISODate("2026-08-15T15:30:00Z")
        }
    },

    {
        upsert: true
    }
);


// Complaint - CMP002

db.complaints_feedback.updateOne(
    {
        complaint_id: "CMP002"
    },

    {
        $setOnInsert: {

            complaint_id: "CMP002",

            customer_id: NumberInt(61),

            vehicle_id: NumberInt(61),

            type: "Complaint",

            message:
                "The service took longer than the originally estimated time.",

            rating: NumberInt(3),

            status: "Resolved",

            submitted_at: ISODate("2026-08-15T16:00:00Z")
        }
    },

    {
        upsert: true
    }
);


// =========================================================
// 5. DIAGNOSTIC SUMMARY
// =========================================================

db.diagnostic_summaries.updateOne(
    {
        diagnostic_id: "DG001"
    },

    {
        $setOnInsert: {

            diagnostic_id: "DG001",

            vehicle_id: NumberInt(61),

            job_card_id: "JC001",

            diagnostic_date: ISODate("2026-08-15T09:00:00Z"),

            mileage: NumberInt(78500),

            battery_voltage: 12.6,

            engine_temperature: 89.5,

            error_codes: [],

            summary:
                "No critical diagnostic faults detected. Vehicle condition is satisfactory."
        }
    },

    {
        upsert: true
    }
);


// =========================================================
// VERIFICATION
// =========================================================

print("");
print("==============================================");
print(" AutoCare MongoDB Sample Data Verification");
print("==============================================");

print(
    "job_cards: " +
    db.job_cards.countDocuments({})
);

print(
    "technician_notes: " +
    db.technician_notes.countDocuments({})
);

print(
    "service_history: " +
    db.service_history.countDocuments({})
);

print(
    "complaints_feedback: " +
    db.complaints_feedback.countDocuments({})
);

print(
    "diagnostic_summaries: " +
    db.diagnostic_summaries.countDocuments({})
);

print("==============================================");
print("Sample data script completed.");
print("==============================================");