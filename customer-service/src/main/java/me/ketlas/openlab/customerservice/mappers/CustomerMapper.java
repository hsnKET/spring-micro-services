package me.ketlas.openlab.customerservice.mappers;


import me.ketlas.openlab.customerservice.dto.CustomerRequestDTO;
import me.ketlas.openlab.customerservice.dto.CustomerResponseDTO;
import me.ketlas.openlab.customerservice.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);
    Customer CustomerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);
}
