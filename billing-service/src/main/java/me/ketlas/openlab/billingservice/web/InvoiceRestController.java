package me.ketlas.openlab.billingservice.web;

import me.ketlas.openlab.billingservice.dto.InvoiceRequestDTO;
import me.ketlas.openlab.billingservice.dto.InvoiceResponseDTO;
import me.ketlas.openlab.billingservice.services.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceRestController {

    private InvoiceService invoiceService;

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @PostMapping("/invoices")
    InvoiceResponseDTO save(@RequestBody InvoiceRequestDTO invoiceRequestDTO){
        return invoiceService.save(invoiceRequestDTO);
    }

    @GetMapping("/invoices")
    List<InvoiceResponseDTO> allInvoices(){
        return invoiceService.allInvoices();
    }

    @GetMapping("/invoices/{id}")
    InvoiceResponseDTO getInvoice(@PathVariable(name ="id") String invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    @GetMapping("/invoices/customers/{customerId}")
    List<InvoiceResponseDTO> invoicesByCustomer(@PathVariable String customerId){
        return invoiceService.invoiceByCustomer(customerId);
    }

}

