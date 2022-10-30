package me.ketlas.openlab.customerservice;

import me.ketlas.openlab.customerservice.dto.CustomerRequestDTO;
import me.ketlas.openlab.customerservice.entities.Customer;
import me.ketlas.openlab.customerservice.mappers.CustomerMapper;
import me.ketlas.openlab.customerservice.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerService customerService, CustomerMapper customerMapper){
        return args -> {

            Stream.of("Jamal","Hamid","Ahmed","Hassan")
                    .forEach(name -> {
                        CustomerRequestDTO customer = CustomerRequestDTO.builder()
                                .email(name+"@gmail.com")
                                .name(name)
                                .id(UUID.randomUUID().toString())
                                .build();
                        customerService.save(customer);
                    });

        };
    }
}
