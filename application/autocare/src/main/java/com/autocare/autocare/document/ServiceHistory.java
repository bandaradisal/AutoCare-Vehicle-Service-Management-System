package com.autocare.autocare.document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "service_history")
public class ServiceHistory {

    @Id
    private String id;

    @Field("history_id")
    private String historyId;

    @Field("vehicle_id")
    private Long vehicleId;

    @Field("booking_id")
    private Long bookingId;

    @Field("service_date")
    private LocalDateTime serviceDate;

    @Field("service_type")
    private String serviceType;

    private Long mileage;

    private String summary;

    /*
     * Existing MongoDB service_history documents
     * store parts_used as an array of strings.
     *
     * Example:
     * [
     *   "Engine Oil",
     *   "Oil Filter",
     *   "Cabin Air Filter"
     * ]
     */
    @Field("parts_used")
    private List<String> partsUsed = new ArrayList<>();


    public ServiceHistory() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }


    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }


    public LocalDateTime getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDateTime serviceDate) {
        this.serviceDate = serviceDate;
    }


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }


    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public List<String> getPartsUsed() {
        return partsUsed;
    }

    public void setPartsUsed(List<String> partsUsed) {
        this.partsUsed = partsUsed;
    }
}