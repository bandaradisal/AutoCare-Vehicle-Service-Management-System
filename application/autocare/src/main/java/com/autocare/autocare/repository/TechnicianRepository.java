package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Technician;

public interface TechnicianRepository
        extends JpaRepository<Technician, Long> {

}