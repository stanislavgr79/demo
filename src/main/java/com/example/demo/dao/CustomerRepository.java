package com.example.demo.dao;

import com.example.demo.domain.entity.person.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer getByUser_Email(String email);

    Customer getById(Long id);

    @Override
    @EntityGraph(value = "Customer.user", type = EntityGraph.EntityGraphType.LOAD)
    List<Customer> findAll();
}
