package me.ketlas.openlab.customerservice.web;

import me.ketlas.openlab.customerservice.dto.CustomerRequestDTO;
import me.ketlas.openlab.customerservice.dto.CustomerResponseDTO;
import me.ketlas.openlab.customerservice.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customers")
    List<CustomerResponseDTO> listCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    CustomerResponseDTO getCustomer(@PathVariable String id){
        return customerService.getCustomer(id);
    }

    @PostMapping("/customers")
    CustomerResponseDTO save(@RequestBody CustomerRequestDTO customerRequestDTO){
        return customerService.save(customerRequestDTO);
    }

    @PutMapping("/customers/{id}")
    CustomerResponseDTO update(@PathVariable String id, @RequestBody CustomerRequestDTO customerRequestDTO){
        customerRequestDTO.setId(id);
        return customerService.update(customerRequestDTO);
    }



}
