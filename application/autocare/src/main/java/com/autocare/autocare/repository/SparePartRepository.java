package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.SparePart;

public interface SparePartRepository
        extends JpaRepository<SparePart, Long> {

}