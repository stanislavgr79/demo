package com.example.demo.controller;

import com.example.demo.domain.model.Basket;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class OrderDetailController {

    @Autowired
    private BasketService basketService;


    @RequestMapping("order/add/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addOrderDetail(@PathVariable(value = "productId") Long productId, HttpSession session) {

        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null){
            basket = new Basket();
            session.setAttribute("basket", basket);
        }
        basketService.addProductToBasket(basket, productId);
        session.setAttribute("basket", basket);

    }

    @RequestMapping("order/removeOrderDetail/{orderDetailByProductId}")
    public String removeOrderDetail(@PathVariable(value = "orderDetailByProductId") Long orderDetailByProductId, HttpSession session) {

        Basket basket = (Basket) session.getAttribute("basket");
        basketService.removeOrderDetailByProductId(basket, orderDetailByProductId);
        return "redirect:/order/getCurrentOrder";
    }

    @RequestMapping("order/removeAllDetails")
    public String removeAllOrderDetails(HttpSession session) {

        Basket basket = (Basket) session.getAttribute("basket");
        basketService.clearOrderDetailList(basket);
        return "redirect:/order/getCurrentOrder";
    }


//    @RequestMapping("order/changeDetailOrderQuantityById/{orderDetailId}/{orderQuantity}")
//    public String changeDetailOrderQuantityById(@Valid @ModelAttribute(value = "Basket") Basket basket,
//                                                HttpSession session,
//                                                @PathVariable(value = "orderDetailId") Long productId,
//                                                @PathVariable(value = "orderQuantity") int quantity) {
//
//        Basket basketSession = (Basket) session.getAttribute("basket");
//
//        return "redirect:/order/getCurrentOrder";
//    }

}

