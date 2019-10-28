package com.example.demo.service.impl;

import com.example.demo.dao.repository.OrderDetailRepository;
import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.CustomerOrder;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.domain.entity.shop.OrderDetail;
import com.example.demo.domain.entity.shop.Product;
import com.example.demo.service.CustomerOrderService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ProductService productService;

    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void removeOrderDetail(Long productOrderId) {
        OrderDetail orderDetail = orderDetailRepository.findById(productOrderId).get();
        orderDetailRepository.delete(orderDetail);
        Order order = orderService.getOrderById(orderDetail.getOrder().getId());
        List<OrderDetail> orderDetails = order.getOrderDetail();
        orderDetails.remove(orderDetail);
        orderService.updateOrder(order);
    }

    @Override
    public void removeAllOrdersDetail(Order order) {
        List<OrderDetail> orderDetails = order.getOrderDetail();
        for (OrderDetail el : orderDetails) {
            removeOrderDetail(el.getId());
        }
    }

    @Override
    public void addOrderDetail(Customer customer, Long productId){
        Order order = customer.getOrder();

        CustomerOrder customerOrder = customerOrderService.getDistinctByOrderId(order.getId());

        if(customerOrder == null){
            customerOrder = new CustomerOrder();
            customerOrder.setOrder(order);
            customerOrder.setCustomer(customer);
            customerOrderService.saveCustomerOrder(customerOrder);
        }

        List<OrderDetail> orderDetails = order.getOrderDetail();

        Product product = productService.getProductById(productId);

        Product search = orderDetails.stream()
                .map(OrderDetail::getProduct)
                .filter(o -> o.equals(product)).findFirst().orElse(null);

        if (search != null) {
            for (OrderDetail orderDetail : orderDetails) {
                if (product.equals(orderDetail.getProduct())) {
                    orderDetail.setQuantity(orderDetail.getQuantity() + 1);
                    orderDetail.setPrice(orderDetail.getQuantity() * orderDetail.getProduct().getProductPrice());
                    saveOrderDetail(orderDetail);
                    break;
                }
            }
        } else {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(1);
            orderDetail.setPrice(product.getProductPrice());
            orderDetail.setOrder(order);
            saveOrderDetail(orderDetail);
        }

        order.setCustomer(customer);
        orderService.updateOrder(order);
    }
}
