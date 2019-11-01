package com.example.demo.domain.entity.shop;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;

//!!
@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;


    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id")
    private Order order;


    public double getSubTotalPrice() {
       double price = quantity * getProductPrice();
                this.setPrice(price);
        return price;
    }

    public double getProductPrice() {
        return product.getProductPrice();
    }
}
