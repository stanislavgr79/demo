package com.example.demo.dao.repository;

import com.example.demo.domain.entity.person.User;
import com.example.demo.domain.entity.shop.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> getOrdersByCustomerNotNull();

    List<Order> findAllByCustomerNotNullOrderByOrderCreateDateDesc();

    List<Order> findAllByCustomer_IdOrderByOrderCreateDateDesc(Long id);

//    @Modifying
//    @Query(value = "UPDATE orders SET statusOrder =?2, orderModifyDate =?3 , manager =?4 WHERE id=?1", nativeQuery = true)
//    void updateOrder(Long id, Order.StatusOrder statusOrder, Date orderModifyDate, User manager);

    @Modifying
    @Query(value = "UPDATE orders SET orderModifyDate =?2, statusOrder=?3, manager=?4 WHERE id=?1", nativeQuery = true)
    void updateOrder(Long id, Date orderModifyDate, int statusOrder, String manager);
}
