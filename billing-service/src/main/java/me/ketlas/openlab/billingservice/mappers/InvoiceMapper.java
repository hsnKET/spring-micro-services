package me.ketlas.openlab.billingservice.mappers;

import me.ketlas.openlab.billingservice.dto.InvoiceRequestDTO;
import me.ketlas.openlab.billingservice.dto.InvoiceResponseDTO;
import me.ketlas.openlab.billingservice.entitties.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice fromInvoiceRequestDTO(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO fromInvoice(Invoice invoice);

}
