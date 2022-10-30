package me.ketlas.openlab.customerservice.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ketlas.openlab.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.Id;


public interface CustomerRepository extends JpaRepository<Customer,String> {
}
