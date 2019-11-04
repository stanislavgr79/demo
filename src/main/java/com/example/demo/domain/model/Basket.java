package com.example.demo.domain.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Basket {

    private double totalPrice;
    private int totalQuantity;

    private List<OrderDetailDTO> orderDetail = new ArrayList<>();

}

