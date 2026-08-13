package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.Customer;
import com.autocare.autocare.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get one customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Add or update customer
    public Customer saveCustomer(Customer customer) {

        // If customer already exists, keep original created date
        if (customer.getCustomerId() != null) {

            Customer existingCustomer =
                    customerRepository.findById(customer.getCustomerId())
                            .orElse(null);

            if (existingCustomer != null) {
                customer.setCreatedDate(
                        existingCustomer.getCreatedDate()
                );
            }

        } else {

            // New customer
            customer.setCreatedDate(LocalDateTime.now());
        }

        return customerRepository.save(customer);
    }

    // Delete customer
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}