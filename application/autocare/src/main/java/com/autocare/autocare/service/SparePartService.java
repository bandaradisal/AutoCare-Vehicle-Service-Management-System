package com.autocare.autocare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.autocare.autocare.entity.SparePart;
import com.autocare.autocare.repository.SparePartRepository;

@Service
public class SparePartService {

    private final SparePartRepository sparePartRepository;

    public SparePartService(
            SparePartRepository sparePartRepository) {

        this.sparePartRepository = sparePartRepository;
    }

    // Get all spare parts
    public List<SparePart> getAllSpareParts() {
        return sparePartRepository.findAll();
    }

    // Get one spare part by ID
    public SparePart getSparePartById(Long id) {
        return sparePartRepository.findById(id).orElse(null);
    }

    // Add or update spare part
    public SparePart saveSparePart(SparePart sparePart) {

        if (sparePart.getPartId() != null) {

            SparePart existingSparePart =
                    sparePartRepository.findById(
                            sparePart.getPartId()
                    ).orElse(null);

            if (existingSparePart != null) {
                sparePart.setCreatedDate(
                        existingSparePart.getCreatedDate()
                );
            }

        } else {

            sparePart.setCreatedDate(LocalDateTime.now());

            if (sparePart.getQuantityInStock() == null) {
                sparePart.setQuantityInStock(0);
            }

            if (sparePart.getReorderLevel() == null) {
                sparePart.setReorderLevel(5);
            }
        }

        return sparePartRepository.save(sparePart);
    }

    // Delete spare part
    public void deleteSparePart(Long id) {
        sparePartRepository.deleteById(id);
    }
}