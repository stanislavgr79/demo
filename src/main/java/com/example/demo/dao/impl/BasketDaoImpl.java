package com.example.demo.dao.impl;


import com.example.demo.dao.BasketDao;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BasketDaoImpl implements BasketDao {

    private static final Logger logger = LoggerFactory.getLogger(BasketDaoImpl.class);

    @Override
    public void clearOrderDetailList(Basket basket) {
        basket.getOrderDetail().clear();
        basket.setTotalPrice(0);
        basket.setTotalQuantity(0);
        logger.info("Basket clear: OrderDetailList, price, quantity - successfully," +
                " Basket ="+ basket +
                " basket_orderDetail_size= " + basket.getOrderDetail().size());
    }

    @Override
    public void removeOrderDetailDTOById(Basket basket, Long id) {
        List<OrderDetailDTO> orderDetail = basket.getOrderDetail();
        logger.info("Size orderDetail before removeOrderDetailDTOBy product_Id," +
                " size= " + orderDetail.size()+ " product_id= " + id);

        for (int x = 0; x < orderDetail.size(); x++) {
            if (orderDetail.get(x).getProduct().getId().equals(id)) {
                orderDetail.remove(x);
                logger.info("In Basket removeOrderDetailDTOById of Product_id successfully," +
                        " basket"+ basket + " product_id= " + id);
            }
        }
        logger.info("Size orderDetail after removeOrderDetailDTOBy product_Id," +
                " size= " + orderDetail.size() + " product_id= " + id);

        basket.setTotalQuantity(getBasketTotalQuantityFromStreamOrderDetails(basket));
        basket.setTotalPrice(getBasketTotalPriceFromStreamOrderDetails(basket));
        logger.info("In Basket update TotalQuantity and TotalPrice, basket= " + basket);
    }

    @Override
    public double getBasketTotalPriceFromStreamOrderDetails(Basket basket) {
        double sum = basket.getOrderDetail().stream()
                .mapToDouble(o -> o.getQuantity() * o.getProduct().getProductPrice())
                .sum();
        return Precision.round(sum, 2);
    }

    @Override
    public int getBasketTotalQuantityFromStreamOrderDetails(Basket basket) {
        return basket.getOrderDetail().stream()
                .mapToInt(OrderDetailDTO::getQuantity)
                .sum();
    }

    @Override
    public void updateSubPriceInOrderDetailListOfBasket(Basket basket) {
        List<OrderDetailDTO> orderDetail = basket.getOrderDetail();
        for (OrderDetailDTO orderDetailDTO : orderDetail) {
//            orderDetailDTO.setSubTotalPrice();
            double temp = orderDetailDTO.getQuantity()*orderDetailDTO.getProduct().getProductPrice();
            orderDetailDTO.setPrice(Precision.round(temp, 2));
        }
        logger.info("Update updateSubPriceInOrderDetailListOfBasket in Basket");
    }
}
