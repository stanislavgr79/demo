package com.example.demo.dao.repository;

import com.example.demo.domain.entity.person.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer getByUser_Email(String email);

    Customer getById(Long id);

}
