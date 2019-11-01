package com.example.demo.domain.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Basket {

    private double totalPrice;
    private int totalQuantity;

    private List<OrderDetailDTO> orderDetail = new ArrayList<>();

//    public void updateSubPriceInOrderDetailList() {
//        for (int x = 0; x < orderDetail.size(); x++) {
//            orderDetail.get(x).setSubTotalPrice();
//        }
//    }


//    public OrderDetailDTO getOrderDetailDTOByProductId(Long id) {
//        OrderDetailDTO orderDetailDTO = null;
//        for (int x = 0; x < orderDetail.size(); x++) {
//            if (orderDetail.get(x).getProduct().getId().equals(id)) {
//                orderDetailDTO = orderDetail.get(x);
//                break;
//            }
//
//        }
//        return orderDetailDTO;
//    }
//
//    public void updateQuantityInOrderDTOinList(OrderDetailDTO orderDetailDTO) {
//        for (int x = 0; x < orderDetail.size(); x++) {
//            if (orderDetail.get(x).getProduct().getId().equals(orderDetailDTO.getProduct().getId())) {
//                orderDetail.get(x).setQuantity(orderDetailDTO.getQuantity());
//                break;
//            }
//        }
//    }


}

