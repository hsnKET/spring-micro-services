package me.ketlas.openlab.billingservice.services;

import me.ketlas.openlab.billingservice.dto.InvoiceRequestDTO;
import me.ketlas.openlab.billingservice.dto.InvoiceResponseDTO;
import me.ketlas.openlab.billingservice.entitties.Invoice;
import me.ketlas.openlab.billingservice.mappers.InvoiceMapper;
import me.ketlas.openlab.billingservice.model.Customer;
import me.ketlas.openlab.billingservice.openfeign.CustomerServiceRestClient;
import me.ketlas.openlab.billingservice.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class InvoiceServiceImpl implements InvoiceService {


    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private CustomerServiceRestClient customerServiceRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerServiceRestClient customerServiceRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerServiceRestClient = customerServiceRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
        Invoice invoice = invoiceMapper.fromInvoiceRequestDTO(invoiceRequestDTO);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.fromInvoice(savedInvoice);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice \"%\" not found!"));
        Customer customer = customerServiceRestClient.getCustomer(invoice.getCustomerID());
        invoice.setCustomer(customer);
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> invoiceByCustomer(String customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerID(customerId);
        List<InvoiceResponseDTO> invoiceResponseDTOS = invoices.stream()
                .map(invoice -> invoiceMapper.fromInvoice(invoice))
                .collect(Collectors.toList());
        return invoiceResponseDTOS;
    }

    @Override
    public List<InvoiceResponseDTO> allInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoice -> invoiceMapper.fromInvoice(invoice))
                .collect(Collectors.toList());
    }
}
