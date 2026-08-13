package com.autocare.autocare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autocare.autocare.entity.InvoiceItem;

public interface InvoiceItemRepository
        extends JpaRepository<InvoiceItem, Long> {

}