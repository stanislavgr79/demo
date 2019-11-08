package com.example.demo.controller;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.*;
import com.example.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/")
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BasketService basketService;

    @RequestMapping(value = "basket/getCurrentBasket", method = RequestMethod.GET)
    public String getCurrentOrder(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if (userDetail == null) {
            return "redirect:/user/registration";
        }

        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null){
            basket = new Basket();
            basket.setIdCustomer(customerService.getCustomerByEmail(auth.getName()).getId());
            session.setAttribute("basket", basket);
        }

        model.addAttribute("Basket", basket);
        logger.info("Basket send to Model, basket= " + basket +
                " customer= " + userDetail.getUsername());
        return "basket";
    }

    @RequestMapping(value = "basket/getCurrentBasket", method = RequestMethod.POST, params = "action1")
    public String updateOrder(@Valid @ModelAttribute(value = "Basket") Basket basket, HttpSession session) {

        List<OrderDetailDTO> list = basket.getOrderDetail();
        Basket basketSession = (Basket) session.getAttribute("basket");
        basketSession.setOrderDetail(list);
        logger.info("Receive Basket and BasketSession for update," +
                " basket= " + basket +
                " basketSession= " + basketSession);
        basketService.updateInfoPriceAndQuantityInBasketSession(basket, basketSession);
        return "redirect:/basket/getCurrentBasket";
    }


    @RequestMapping(value = "basket/getCurrentBasket", method = RequestMethod.POST, params = "action2")
    public String saveOrder(@Valid @ModelAttribute(value = "Basket") Basket basket, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);

        basketService.updateInfoPriceAndQuantityInBasket(basket);
        orderService.createOrderFromBasket(customer, basket);
        session.removeAttribute("basket");

        logger.info("Take Basket and send with current Customer for create Order," +
                " basket= " + basket +
                " customer= " + customer);
        return "redirect:/getAllProducts";
    }

    @RequestMapping("basket/add/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addOrderDetail(@PathVariable(value = "productId") Long productId, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null){
            basket = new Basket();
            basket.setIdCustomer(customerService.getCustomerByEmail(auth.getName()).getId());
            session.setAttribute("basket", basket);
        }
        basketService.addProductToBasket(basket, productId);
        session.setAttribute("basket", basket);

        logger.info("Take ProductId and send to current basket, basket= " + basket + " productId= " + productId);
    }

    @RequestMapping("basket/removeOrderDetail/{orderDetailByProductId}")
    public String removeOrderDetail(@PathVariable(value = "orderDetailByProductId") Long orderDetailByProductId, HttpSession session) {

        Basket basket = (Basket) session.getAttribute("basket");
        basketService.removeOrderDetailByProductId(basket, orderDetailByProductId);
        logger.info("Take orderDetailId for remove from basket," +
                " basket= " + basket + " orderDetailId= " + orderDetailByProductId);
        return "redirect:/basket/getCurrentBasket";
    }

    @RequestMapping("basket/removeAllDetails")
    public String removeAllOrderDetails(HttpSession session) {

        Basket basket = (Basket) session.getAttribute("basket");
        basketService.clearOrderDetailList(basket);
        logger.info("Clear all orderDetail from basketSession, basket= " + basket);
        return "redirect:/basket/getCurrentBasket";
    }
}
