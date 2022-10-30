package me.ketlas.openlab.billingservice.services;

import me.ketlas.openlab.billingservice.dto.InvoiceRequestDTO;
import me.ketlas.openlab.billingservice.dto.InvoiceResponseDTO;
import me.ketlas.openlab.billingservice.entitties.Invoice;

import java.util.List;

public interface InvoiceService {

    InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoice(String id);
    List<InvoiceResponseDTO> invoiceByCustomer(String customerId);
    List<InvoiceResponseDTO> allInvoices();
}
