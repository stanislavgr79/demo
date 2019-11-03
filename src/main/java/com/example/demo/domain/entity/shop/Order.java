package com.example.demo.domain.entity.shop;


import com.example.demo.domain.entity.person.Customer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@EqualsAndHashCode
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private double totalPrice;

    private int totalQuantity;

//    public void addOrderDetail(OrderDetail orderDetail) {
//        orderDetail.setOrder(this);
//        this.orderDetail.add(orderDetail);
//    }
//
//    public void removeOrderDetail(OrderDetail orderDetail) {
//        this.orderDetail.remove(orderDetail);
//        orderDetail.setOrder(null);
//    }
//
//    public void clearOrderDetail() {
//        for (OrderDetail od : orderDetail) {
//            od.setOrder(null);
//        }
//        orderDetail.clear();
//    }

}