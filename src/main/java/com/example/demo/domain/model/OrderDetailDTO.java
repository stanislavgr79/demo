package com.example.demo.domain.model;

import com.example.demo.domain.entity.shop.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = "product")
public class OrderDetailDTO {

    private int quantity;
    private double price;

    private Product product;
}
