package com.autocare.autocare.document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "diagnostic_summaries")
public class DiagnosticSummary {

    @Id
    private String id;

    @Field("diagnostic_id")
    private String diagnosticId;

    @Field("vehicle_id")
    private Long vehicleId;

    @Field("job_card_id")
    private String jobCardId;

    @Field("diagnostic_date")
    private LocalDateTime diagnosticDate;

    private Long mileage;

    @Field("battery_voltage")
    private Double batteryVoltage;

    @Field("engine_temperature")
    private Double engineTemperature;

    private String summary;

    @Field("error_codes")
    private List<String> errorCodes = new ArrayList<>();


    public DiagnosticSummary() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDiagnosticId() {
        return diagnosticId;
    }

    public void setDiagnosticId(String diagnosticId) {
        this.diagnosticId = diagnosticId;
    }


    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }


    public String getJobCardId() {
        return jobCardId;
    }

    public void setJobCardId(String jobCardId) {
        this.jobCardId = jobCardId;
    }


    public LocalDateTime getDiagnosticDate() {
        return diagnosticDate;
    }

    public void setDiagnosticDate(LocalDateTime diagnosticDate) {
        this.diagnosticDate = diagnosticDate;
    }


    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }


    public Double getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Double batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }


    public Double getEngineTemperature() {
        return engineTemperature;
    }

    public void setEngineTemperature(Double engineTemperature) {
        this.engineTemperature = engineTemperature;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}