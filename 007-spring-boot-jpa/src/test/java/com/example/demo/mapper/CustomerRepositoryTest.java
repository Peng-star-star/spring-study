package com.example.demo.mapper;

import com.example.demo.domain.Customer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Order(1)
    public void init() {
        customerRepository.save(new Customer("Jack", "Bauer"));
        customerRepository.save(new Customer("Chloe", "O'Brian"));
    }

    @Test
    public void findByLastName() {
        List<Customer> customers = customerRepository.findByLastName("Bauer");
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    public void findById() {
        Customer customer = customerRepository.findById(1L);
        assertThat(customer.getFirstName()).isEqualTo("Jack");
    }
}
