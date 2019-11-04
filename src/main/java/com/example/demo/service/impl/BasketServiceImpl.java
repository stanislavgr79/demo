package com.example.demo.service.impl;

import com.example.demo.dao.BasketDao;
import com.example.demo.dao.OrderDetailDTO_Dao;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.service.BasketService;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private static final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketDao basketDao;

    @Autowired
    private OrderDetailDTO_Dao orderDetailDTO_dao;



    @Override
    public void addProductToBasket(Basket basket, Long productId) {

        Product product = productService.getProductById(productId);
        orderDetailDTO_dao.addProductToOrderDetailDTO(basket, product);
        logger.info("Product find successfully, Product ="+ product);
    }

    @Override
    public void clearOrderDetailList(Basket basket) {
        basketDao.clearOrderDetailList(basket);
    }

    @Override
    public void removeOrderDetailByProductId(Basket basket, Long orderDetailByProductId) {
        basketDao.removeOrderDetailDTOById(basket, orderDetailByProductId);
    }

    @Override
    public void updateInfoPriceAndQuantity(Basket basket, Basket basketSession) {
        basketDao.updateSubPriceInOrderDetailList(basketSession);
        basketSession.setTotalQuantity(basketDao.getBasketTotalQuantity(basket));
        basketSession.setTotalPrice(basketDao.getBasketTotalPrice(basket));

    }
}
