package com.example.demo.dao.repository;

import com.example.demo.domain.entity.shop.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> getOrdersByCustomerNotNull();
}
