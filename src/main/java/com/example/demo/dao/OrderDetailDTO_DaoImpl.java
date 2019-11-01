package com.example.demo.dao;

import com.example.demo.domain.entity.shop.Product;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailDTO_DaoImpl implements OrderDetailDTO_Dao{

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
                    el.setPrice(el.getQuantity() * el.getProduct().getProductPrice());
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

        basket.setTotalQuantity(basketDao.getBasketTotalQuantity(basket));
        basket.setTotalPrice(basketDao.getBasketTotalPrice(basket));
    }
}
