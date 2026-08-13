package com.autocare.autocare.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autocare.autocare.entity.InvoiceItem;
import com.autocare.autocare.entity.SparePart;
import com.autocare.autocare.repository.InvoiceItemRepository;
import com.autocare.autocare.repository.SparePartRepository;

@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final SparePartRepository sparePartRepository;

    public InvoiceItemService(
            InvoiceItemRepository invoiceItemRepository,
            SparePartRepository sparePartRepository) {

        this.invoiceItemRepository = invoiceItemRepository;
        this.sparePartRepository = sparePartRepository;
    }

    // Get all invoice items
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemRepository.findAll();
    }

    // Get one invoice item
    public InvoiceItem getInvoiceItemById(Long id) {
        return invoiceItemRepository.findById(id).orElse(null);
    }

    // Add or update invoice item
    @Transactional
    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {

        SparePart selectedPart =
                sparePartRepository.findById(
                        invoiceItem.getSparePart().getPartId()
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "Spare part not found."
                        )
                );

        // NEW INVOICE ITEM
        if (invoiceItem.getInvoiceItemId() == null) {

            if (invoiceItem.getQuantity() == null
                    || invoiceItem.getQuantity() <= 0) {

                throw new IllegalArgumentException(
                        "Quantity must be greater than zero."
                );
            }

            /*
             * Use current spare-part price.
             * Oracle INSERT trigger will reduce stock.
             */
            invoiceItem.setUnitPrice(
                    selectedPart.getUnitPrice()
            );

        } else {

            // EDIT EXISTING INVOICE ITEM
            InvoiceItem existingItem =
                    invoiceItemRepository.findById(
                            invoiceItem.getInvoiceItemId()
                    ).orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Invoice item not found."
                            )
                    );

            SparePart oldPart =
                    sparePartRepository.findById(
                            existingItem.getSparePart().getPartId()
                    ).orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Original spare part not found."
                            )
                    );

            int oldQuantity = existingItem.getQuantity();
            int newQuantity = invoiceItem.getQuantity();

            Long oldPartId = oldPart.getPartId();
            Long newPartId = selectedPart.getPartId();

            // Same spare part, only quantity changed
            if (oldPartId.equals(newPartId)) {

                int difference =
                        newQuantity - oldQuantity;

                // More items are being used
                if (difference > 0) {

                    if (selectedPart.getQuantityInStock()
                            < difference) {

                        throw new IllegalArgumentException(
                                "Insufficient spare-part stock."
                        );
                    }

                    selectedPart.setQuantityInStock(
                            selectedPart.getQuantityInStock()
                                    - difference
                    );
                }

                // Fewer items are being used
                if (difference < 0) {

                    selectedPart.setQuantityInStock(
                            selectedPart.getQuantityInStock()
                                    + Math.abs(difference)
                    );
                }

                sparePartRepository.save(selectedPart);

                // Keep original captured unit price
                invoiceItem.setUnitPrice(
                        existingItem.getUnitPrice()
                );

            } else {

                /*
                 * Spare part changed.
                 *
                 * Restore stock of old part.
                 */
                oldPart.setQuantityInStock(
                        oldPart.getQuantityInStock()
                                + oldQuantity
                );

                sparePartRepository.save(oldPart);

                // Check new part stock
                if (selectedPart.getQuantityInStock()
                        < newQuantity) {

                    throw new IllegalArgumentException(
                            "Insufficient stock for selected spare part."
                    );
                }

                // Reduce stock of new part
                selectedPart.setQuantityInStock(
                        selectedPart.getQuantityInStock()
                                - newQuantity
                );

                sparePartRepository.save(selectedPart);

                invoiceItem.setUnitPrice(
                        selectedPart.getUnitPrice()
                );
            }
        }

        // Calculate subtotal
        BigDecimal subtotal =
                invoiceItem.getUnitPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        invoiceItem.getQuantity()
                                )
                        );

        invoiceItem.setSubtotal(subtotal);

        /*
         * Oracle TRG_INVOICE_ITEM_SUBTOTAL
         * also calculates this value.
         */
        return invoiceItemRepository.save(invoiceItem);
    }

    // Delete invoice item and restore stock
    @Transactional
    public void deleteInvoiceItem(Long id) {

        InvoiceItem invoiceItem =
                invoiceItemRepository.findById(id)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Invoice item not found."
                                )
                        );

        SparePart sparePart =
                sparePartRepository.findById(
                        invoiceItem.getSparePart().getPartId()
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "Spare part not found."
                        )
                );

        // Restore the stock
        sparePart.setQuantityInStock(
                sparePart.getQuantityInStock()
                        + invoiceItem.getQuantity()
        );

        sparePartRepository.save(sparePart);

        // Delete invoice item
        invoiceItemRepository.delete(invoiceItem);
    }
}