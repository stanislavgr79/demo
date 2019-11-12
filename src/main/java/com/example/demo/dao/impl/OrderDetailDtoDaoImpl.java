package com.example.demo.dao.impl;

import com.example.demo.dao.BasketDao;
import com.example.demo.dao.OrderDetailDtoDao;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailDtoDaoImpl implements OrderDetailDtoDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailDtoDaoImpl.class);

    @Autowired
    private BasketDao basketDao;

    @Override
    public void addProductToOrderDetailDTO(Basket basket, Product product) {

        List<OrderDetailDTO> orderDetailDTOList = basket.getOrderDetail();

        Product search = orderDetailDTOList.stream()
                .map(OrderDetailDTO::getProduct)
                .filter(o -> o.equals(product)).findFirst().orElse(null);

        if (search != null) {
            for (OrderDetailDTO el : orderDetailDTOList) {
                if (product.equals(el.getProduct())) {
                    el.setQuantity(el.getQuantity() + 1);
                    double temp = el.getQuantity() * el.getProduct().getProductPrice();
                    el.setPrice(Precision.round(temp, 2));
                    break;
                }
            }
        } else {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setProduct(product);
            orderDetailDTO.setQuantity(1);
            orderDetailDTO.setPrice(product.getProductPrice());
            orderDetailDTOList.add(orderDetailDTO);
        }

        basket.setOrderDetail(orderDetailDTOList);

        basket.setTotalQuantity(basketDao.getBasketTotalQuantityFromStreamOrderDetails(basket));
        basket.setTotalPrice(basketDao.getBasketTotalPriceFromStreamOrderDetails(basket));
        logger.info("Product add to Basket, and update TotalQuantity, TotalPrice in Basket; " +
                "product= " + product + "basket= " + basket);
    }
}
