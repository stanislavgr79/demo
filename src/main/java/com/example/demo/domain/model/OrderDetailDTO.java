package com.example.demo.domain.model;

import com.example.demo.domain.entity.shop.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "product")
public class OrderDetailDTO {

    private int quantity;
    private double price;

    private Product product;

    public double getSubTotalPrice() {
        return quantity * getProductPrice();
    }

    public double getProductPrice() {
        return product.getProductPrice();
    }

    public void setSubTotalPrice(){
        this.price = quantity * getProductPrice();
    }
}
