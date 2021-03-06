package com.example.demo.domain.entity.shop;

import com.example.demo.domain.entity.person.Customer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = {"totalPrice", "totalQuantity", "customer"})
@ToString(exclude = {"orderDetail", "customer"})
@NamedEntityGraph(name = "Order.customer",
        attributeNodes = @NamedAttributeNode(value = "customer", subgraph = "customer"),
        subgraphs = @NamedSubgraph(name = "customer", attributeNodes = @NamedAttributeNode("user")))
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;
    private int totalQuantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderCreateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderModifyDate;

    private String manager;

    private StatusOrder statusOrder;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Getter
    @RequiredArgsConstructor
    @ToString
    public enum StatusOrder implements Serializable{
        NOT_READY("in process"), READY("ready to delivery");
        private final String STATUS;
    }
}