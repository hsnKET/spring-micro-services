package me.ketlas.openlab.customerservice.services;

import me.ketlas.openlab.customerservice.dto.CustomerRequestDTO;
import me.ketlas.openlab.customerservice.dto.CustomerResponseDTO;
import me.ketlas.openlab.customerservice.entities.Customer;
import me.ketlas.openlab.customerservice.mappers.CustomerMapper;
import me.ketlas.openlab.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerResponseDTO save(CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.CustomerRequestDTOToCustomer(customerRequestDTO);
        customer.setId(UUID.randomUUID().toString());
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO getCustomer(String id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Customer with id \"%s\" not found",id)));

        return customerMapper.customerToCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO update(CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.CustomerRequestDTOToCustomer(customerRequestDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerResponseDTO(savedCustomer);
    }

    @Override
    public List<CustomerResponseDTO> listCustomers() {
        List<CustomerResponseDTO> result = customerRepository.findAll()
                .stream()
                .map(customer -> {
                    return customerMapper.customerToCustomerResponseDTO(customer);
                }).collect(Collectors.toList());
        return result;
    }
}
