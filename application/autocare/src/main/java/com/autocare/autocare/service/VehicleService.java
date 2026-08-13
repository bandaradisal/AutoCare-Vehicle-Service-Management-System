package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.Vehicle;
import com.autocare.autocare.repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get one vehicle by ID
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    // Add or update vehicle
    public Vehicle saveVehicle(Vehicle vehicle) {

        if (vehicle.getVehicleId() != null) {

            Vehicle existingVehicle =
                    vehicleRepository.findById(vehicle.getVehicleId())
                            .orElse(null);

            if (existingVehicle != null) {
                vehicle.setCreatedDate(
                        existingVehicle.getCreatedDate()
                );
            }

        } else {

            vehicle.setCreatedDate(LocalDateTime.now());
        }

        return vehicleRepository.save(vehicle);
    }

    // Delete vehicle
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}