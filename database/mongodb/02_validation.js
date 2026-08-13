// =========================================================
// AutoCare Vehicle Service & Maintenance Management System
// MongoDB Script 02 - Collection Validation
// =========================================================

// Select AutoCare MongoDB database
db = db.getSiblingDB("autocare_mongodb");


// =========================================================
// 1. JOB CARDS VALIDATION
// =========================================================

db.runCommand({
    collMod: "job_cards",

    validator: {
        $jsonSchema: {
            bsonType: "object",

            required: [
                "job_card_id",
                "booking_id",
                "vehicle_id",
                "technician_id",
                "opened_date",
                "status"
            ],

            properties: {

                job_card_id: {
                    bsonType: "string",
                    description: "Unique job card ID"
                },

                booking_id: {
                    bsonType: ["int", "long"],
                    description: "Oracle service booking ID"
                },

                vehicle_id: {
                    bsonType: ["int", "long"],
                    description: "Oracle vehicle ID"
                },

                technician_id: {
                    bsonType: ["int", "long"],
                    description: "Oracle technician ID"
                },

                opened_date: {
                    bsonType: "date"
                },

                status: {
                    enum: [
                        "Open",
                        "In Progress",
                        "Completed",
                        "Cancelled"
                    ]
                },

                tasks: {
                    bsonType: "array",

                    items: {
                        bsonType: "object",

                        properties: {

                            description: {
                                bsonType: "string"
                            },

                            status: {
                                bsonType: "string"
                            }
                        }
                    }
                }
            }
        }
    },

    validationLevel: "strict",
    validationAction: "error"
});


// =========================================================
// 2. TECHNICIAN NOTES VALIDATION
// =========================================================

db.runCommand({
    collMod: "technician_notes",

    validator: {
        $jsonSchema: {
            bsonType: "object",

            required: [
                "note_id",
                "job_card_id",
                "technician_id",
                "created_at",
                "notes"
            ],

            properties: {

                note_id: {
                    bsonType: "string"
                },

                job_card_id: {
                    bsonType: "string"
                },

                technician_id: {
                    bsonType: ["int", "long"]
                },

                created_at: {
                    bsonType: "date"
                },

                notes: {
                    bsonType: "array",

                    items: {
                        bsonType: "string"
                    }
                }
            }
        }
    },

    validationLevel: "strict",
    validationAction: "error"
});


// =========================================================
// 3. SERVICE HISTORY VALIDATION
// =========================================================

db.runCommand({
    collMod: "service_history",

    validator: {
        $jsonSchema: {
            bsonType: "object",

            required: [
                "history_id",
                "vehicle_id",
                "booking_id",
                "service_date",
                "service_type",
                "mileage"
            ],

            properties: {

                history_id: {
                    bsonType: "string"
                },

                vehicle_id: {
                    bsonType: ["int", "long"]
                },

                booking_id: {
                    bsonType: ["int", "long"]
                },

                service_date: {
                    bsonType: "date"
                },

                service_type: {
                    bsonType: "string"
                },

                mileage: {
                    bsonType: ["int", "long"],
                    minimum: 0
                },

                summary: {
                    bsonType: "string"
                },

                parts_used: {
                    bsonType: "array",

                    items: {
                        bsonType: "string"
                    }
                }
            }
        }
    },

    validationLevel: "strict",
    validationAction: "error"
});


// =========================================================
// 4. COMPLAINTS AND FEEDBACK VALIDATION
// =========================================================

db.runCommand({
    collMod: "complaints_feedback",

    validator: {
        $jsonSchema: {
            bsonType: "object",

            required: [
                "complaint_id",
                "customer_id",
                "vehicle_id",
                "type",
                "message",
                "status",
                "submitted_at"
            ],

            properties: {

                complaint_id: {
                    bsonType: "string"
                },

                customer_id: {
                    bsonType: ["int", "long"]
                },

                vehicle_id: {
                    bsonType: ["int", "long"]
                },

                type: {
                    enum: [
                        "Complaint",
                        "Feedback"
                    ]
                },

                message: {
                    bsonType: "string"
                },

                rating: {
                    bsonType: ["int", "long"],
                    minimum: 1,
                    maximum: 5
                },

                status: {
                    enum: [
                        "Open",
                        "Under Review",
                        "Resolved",
                        "Closed"
                    ]
                },

                submitted_at: {
                    bsonType: "date"
                }
            }
        }
    },

    validationLevel: "strict",
    validationAction: "error"
});


// =========================================================
// 5. DIAGNOSTIC SUMMARY VALIDATION
// =========================================================

db.runCommand({
    collMod: "diagnostic_summaries",

    validator: {
        $jsonSchema: {
            bsonType: "object",

            required: [
                "diagnostic_id",
                "vehicle_id",
                "diagnostic_date",
                "summary"
            ],

            properties: {

                diagnostic_id: {
                    bsonType: "string"
                },

                vehicle_id: {
                    bsonType: ["int", "long"]
                },

                job_card_id: {
                    bsonType: "string"
                },

                diagnostic_date: {
                    bsonType: "date"
                },

                mileage: {
                    bsonType: ["int", "long"],
                    minimum: 0
                },

                battery_voltage: {
                    bsonType: ["double", "int", "long", "decimal"]
                },

                engine_temperature: {
                    bsonType: ["double", "int", "long", "decimal"]
                },

                error_codes: {
                    bsonType: "array",

                    items: {
                        bsonType: "string"
                    }
                },

                summary: {
                    bsonType: "string"
                }
            }
        }
    },

    validationLevel: "strict",
    validationAction: "error"
});

// =========================================================
// End of Script 02
// =========================================================