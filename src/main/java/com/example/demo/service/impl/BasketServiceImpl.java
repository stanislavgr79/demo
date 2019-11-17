package com.example.demo.service.impl;

import com.example.demo.domain.logic.BasketLogic;
import com.example.demo.domain.logic.OrderDetailDtoLogic;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.service.BasketService;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BasketServiceImpl implements BasketService {

    private static final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketLogic basketLogic;

    @Autowired
    private OrderDetailDtoLogic orderDetailDtoLogic;


    @Override
    public void addProductToBasket(Basket basket, Long productId) {
        Product product = productService.getProductById(productId);
        orderDetailDtoLogic.addProductToOrderDetailDTO(basket, product);
        logger.info("Product find successfully by Id ="+ product +
                " and add to Basket = "+ basket);
    }

    @Override
    public void clearOrderDetailList(Basket basket) {
        basketLogic.clearOrderDetailList(basket);
        logger.info("ClearOrderDetailList in Basket = "+ basket);
    }

    @Override
    public void removeOrderDetailByProductId(Basket basket, Long orderDetailByProductId) {
        basketLogic.removeOrderDetailDTOById(basket, orderDetailByProductId);
        logger.info("RemoveOrderDetailByProductId = " + orderDetailByProductId +
                " in Basket = " + basket);
    }

    @Override
    public void updateInfoPriceAndQuantityInBasketSession(Basket basket, Basket basketSession) {
        basketLogic.updateSubPriceInOrderDetailListOfBasket(basketSession);
        basketSession.setTotalQuantity(basketLogic.getBasketTotalQuantityFromStreamOrderDetails(basket));
        basketSession.setTotalPrice(basketLogic.getBasketTotalPriceFromStreamOrderDetails(basket));
        logger.info("UpdateInfoPriceAndQuantity in BasketSession = "+ basketSession);
    }

    @Override
    public void updateInfoPriceAndQuantityInBasket(Basket basket){
        basketLogic.updateSubPriceInOrderDetailListOfBasket(basket);
        basket.setTotalQuantity(basketLogic.getBasketTotalQuantityFromStreamOrderDetails(basket));
        basket.setTotalPrice(basketLogic.getBasketTotalPriceFromStreamOrderDetails(basket));
        logger.info("UpdateInfoPriceAndQuantity in Basket = "+ basket);
    }

}
