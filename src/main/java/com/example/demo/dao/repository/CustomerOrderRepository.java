package com.example.demo.dao.repository;

import com.example.demo.domain.entity.shop.CustomerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {

    CustomerOrder getDistinctByOrderId(Long id);
}
