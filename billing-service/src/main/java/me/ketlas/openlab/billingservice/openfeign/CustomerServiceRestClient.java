package me.ketlas.openlab.billingservice.openfeign;


import me.ketlas.openlab.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerServiceRestClient {

    @GetMapping("/api/customers/{id}")
    Customer getCustomer(@PathVariable(name = "id") String id);

    @GetMapping("/api/customers")
    List<Customer> listCustomers();


}
