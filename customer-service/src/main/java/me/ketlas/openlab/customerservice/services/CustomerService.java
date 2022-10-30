package me.ketlas.openlab.customerservice.services;

import me.ketlas.openlab.customerservice.dto.CustomerRequestDTO;
import me.ketlas.openlab.customerservice.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO getCustomer(String id);
    CustomerResponseDTO update(CustomerRequestDTO customerRequestDTO);
    List<CustomerResponseDTO> listCustomers();
}
