package com.example.demo.dao;


import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketDaoImpl implements BasketDao {


    @Override
    public void clearOrderDetailList(Basket basket) {
        basket.getOrderDetail().clear();
        basket.setTotalPrice(0);
        basket.setTotalQuantity(0);
    }

    @Override
    public void removeOrderDetailDTOById(Basket basket, Long id) {
        List<OrderDetailDTO> orderDetail = basket.getOrderDetail();
        for (int x = 0; x < orderDetail.size(); x++) {
            if (orderDetail.get(x).getProduct().getId().equals(id)) {
                orderDetail.remove(x);
            }
        }
        basket.setTotalQuantity(getBasketTotalQuantity(basket));
        basket.setTotalPrice(getBasketTotalPrice(basket));
    }

    @Override
    public double getBasketTotalPrice(Basket basket) {

        return basket.getOrderDetail().stream()
                .mapToDouble(o -> o.getQuantity() * o.getProductPrice())
                .sum();
    }

    @Override
    public int getBasketTotalQuantity(Basket basket) {

        return basket.getOrderDetail().stream()
                .mapToInt(OrderDetailDTO::getQuantity)
                .sum();
    }

    public void updateSubPriceInOrderDetailList(Basket basket) {
        List<OrderDetailDTO> orderDetail = basket.getOrderDetail();
        for (int x = 0; x < orderDetail.size(); x++) {
            orderDetail.get(x).setSubTotalPrice();
        }
    }
}
