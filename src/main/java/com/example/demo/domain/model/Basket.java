package com.example.demo.domain.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(exclude = "idCustomer")
@ToString(exclude = "orderDetail")
public class Basket {

    private Long idCustomer;

    private double totalPrice;
    private int totalQuantity;

    @Builder.Default
    private List<OrderDetailDTO> orderDetail = new ArrayList<>();

}

