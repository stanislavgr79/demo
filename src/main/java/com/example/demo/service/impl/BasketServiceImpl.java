package com.example.demo.service.impl;

import com.example.demo.dao.BasketDao;
import com.example.demo.dao.OrderDetailDtoDao;
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
    private BasketDao basketDao;

    @Autowired
    private OrderDetailDtoDao orderDetailDtoDao;


    @Override
    public void addProductToBasket(Basket basket, Long productId) {

        Product product = productService.getProductById(productId);
        orderDetailDtoDao.addProductToOrderDetailDTO(basket, product);
        logger.info("Product find successfully by Id ="+ product +
                " and add to Basket = "+ basket);
    }

    @Override
    public void clearOrderDetailList(Basket basket) {
        basketDao.clearOrderDetailList(basket);
        logger.info("ClearOrderDetailList in Basket = "+ basket);
    }

    @Override
    public void removeOrderDetailByProductId(Basket basket, Long orderDetailByProductId) {
        basketDao.removeOrderDetailDTOById(basket, orderDetailByProductId);
        logger.info("RemoveOrderDetailByProductId = " + orderDetailByProductId +
                " in Basket = " + basket);
    }

    @Override
    public void updateInfoPriceAndQuantityInBasketSession(Basket basket, Basket basketSession) {
        basketDao.updateSubPriceInOrderDetailList(basketSession);
        basketSession.setTotalQuantity(basketDao.getBasketTotalQuantity(basket));
        basketSession.setTotalPrice(basketDao.getBasketTotalPrice(basket));
        logger.info("UpdateInfoPriceAndQuantity in BasketSession = "+ basketSession);
    }

    @Override
    public void updateInfoPriceAndQuantityInBasket(Basket basket){
        basketDao.updateSubPriceInOrderDetailList(basket);
        basket.setTotalQuantity(basketDao.getBasketTotalQuantity(basket));
        basket.setTotalPrice(basketDao.getBasketTotalPrice(basket));
    }

}
