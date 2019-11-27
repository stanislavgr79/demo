package com.example.demo.dao;

import com.example.demo.domain.entity.shop.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Order getById(Long id);

    @EntityGraph(value = "Order.customer", type = EntityGraph.EntityGraphType.LOAD)
    List<Order> findAllByCustomerNotNullOrderByOrderCreateDateDesc();

    @EntityGraph(value = "Order.customer", type = EntityGraph.EntityGraphType.LOAD)
    List<Order> findAllByCustomer_IdOrderByOrderCreateDateDesc(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET orderModifyDate =?2, statusOrder=?3, manager=?4 WHERE id=?1", nativeQuery = true)
    void updateOrder(Long id, Date orderModifyDate, int statusOrder, String manager);
}
