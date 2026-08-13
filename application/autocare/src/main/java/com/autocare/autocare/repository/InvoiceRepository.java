package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.Invoice;

public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

}