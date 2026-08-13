// =========================================================
// AutoCare Vehicle Service & Maintenance Management System
// MongoDB Script 04 - Queries
// =========================================================

db = db.getSiblingDB("autocare_mongodb");


// 1. View all job cards
db.job_cards.find();


// 2. Find job cards for a specific vehicle
db.job_cards.find({
    vehicle_id: NumberInt(61)
});


// 3. Find completed job cards
db.job_cards.find({
    status: "Completed"
});


// 4. View technician notes for a job card
db.technician_notes.find({
    job_card_id: "JC001"
});


// 5. View service history for a vehicle
db.service_history.find({
    vehicle_id: NumberInt(61)
});


// 6. View complaints only
db.complaints_feedback.find({
    type: "Complaint"
});


// 7. View feedback only
db.complaints_feedback.find({
    type: "Feedback"
});


// 8. View unresolved complaints
db.complaints_feedback.find({
    status: {
        $in: ["Open", "Under Review"]
    }
});


// 9. Find high-rated feedback
db.complaints_feedback.find({
    rating: {
        $gte: NumberInt(4)
    }
});


// 10. View diagnostics for a vehicle
db.diagnostic_summaries.find({
    vehicle_id: NumberInt(61)
});


// 11. Find diagnostics containing error codes
db.diagnostic_summaries.find({
    error_codes: {
        $exists: true,
        $ne: []
    }
});


// 12. Sort service history by latest service date
db.service_history
  .find({
      vehicle_id: NumberInt(61)
  })
  .sort({
      service_date: -1
  });


// 13. Count complaints and feedback by type
db.complaints_feedback.aggregate([
    {
        $group: {
            _id: "$type",
            total: {
                $sum: 1
            }
        }
    }
]);


// 14. Count job cards by status
db.job_cards.aggregate([
    {
        $group: {
            _id: "$status",
            total: {
                $sum: 1
            }
        }
    }
]);


// 15. Show only selected fields from job cards
db.job_cards.find(
    {},
    {
        _id: 0,
        job_card_id: 1,
        vehicle_id: 1,
        technician_id: 1,
        status: 1
    }
);