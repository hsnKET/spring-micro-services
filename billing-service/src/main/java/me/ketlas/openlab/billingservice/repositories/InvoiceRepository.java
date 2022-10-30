package me.ketlas.openlab.billingservice.repositories;

import me.ketlas.openlab.billingservice.entitties.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice,String> {
    List<Invoice> findByCustomerID(String customerId);
}
