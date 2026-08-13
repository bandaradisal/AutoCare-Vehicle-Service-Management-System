package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}