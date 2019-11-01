package com.example.demo.controller;

import com.example.demo.dao.BasketDao;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.model.Basket;
import com.example.demo.domain.model.OrderDetailDTO;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketDao basketDao;

    @Autowired
    private BasketService basketService;

    @RequestMapping(value = "order/getCurrentOrder", method = RequestMethod.GET)
    public String getCurrentOrder(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if (userDetail == null) {
            return "redirect:/user/registration";
        }

        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null){
            basket = new Basket();
            session.setAttribute("basket", basket);
        }

        model.addAttribute("Basket", basket);
        return "order";
    }


    @RequestMapping(value = "order/getCurrentOrder", method = RequestMethod.POST, params = "action1")
    public String updateOrder(@Valid @ModelAttribute(value = "Basket") Basket basket, HttpSession session) {

        List<OrderDetailDTO> list = basket.getOrderDetail();
        Basket basketSession = (Basket) session.getAttribute("basket");
        basketSession.setOrderDetail(list);

        basketService.updateInfoPriceAndQuantity(basket, basketSession);
        return "redirect:/order/getCurrentOrder";
    }


    @RequestMapping(value = "order/getCurrentOrder", method = RequestMethod.POST, params = "action2")
    public String saveOrder(@Valid @ModelAttribute(value = "Basket") Basket basket, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Customer customer = customerService.getCustomerByEmail(email);

        List<OrderDetailDTO> list = basket.getOrderDetail();
        Basket basketSession = (Basket) session.getAttribute("basket");
        basketSession.setOrderDetail(list);



        basket.setTotalQuantity(basketDao.getBasketTotalQuantity(basket));
        basket.setTotalPrice(basketDao.getBasketTotalPrice(basket));
        basketDao.updateSubPriceInOrderDetailList(basketSession);


        Order order = new Order();
        order.setTotalQuantity(basketSession.getTotalQuantity());
        order.setTotalPrice(basketSession.getTotalPrice());

        order.setCustomer(customer);
        customerService.updateCustomer(customer);

        orderService.saveOrder(order);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrder(order);
        customerOrder.setCustomer(customer);
        customerOrderService.saveCustomerOrder(customerOrder);


        List<OrderDetail> orderDetails = order.getOrderDetail();

        for (OrderDetailDTO el: list){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(productService.getProductById(el.getProduct().getId()));
            orderDetail.setQuantity(el.getQuantity());
            orderDetail.setPrice(el.getPrice());
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);
            orderDetailService.saveOrderDetail(orderDetail);
        }

        order.setCustomer(customer);
        order.setOrderDetail(orderDetails);
        orderService.saveOrder(order);

        customer.addOrder(order);

        customerService.updateCustomer(customer);

        session.removeAttribute("basket");

        return "redirect:/getAllProducts";
    }


    @RequestMapping("order/{orderId}")
    public @ResponseBody
    Order getOrder(@PathVariable(value = "orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

}
