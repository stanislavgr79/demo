package com.example.demo.mail;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;

import javax.mail.MessagingException;

public interface EmailService {

    void sendRegisterMail(
            final String recipientName, final String recipientEmail, String password) throws MessagingException;

    void sendOrderMailToRecipient(final Customer customer, final Order order) throws MessagingException;


    void sendOrderUpdateStatusToRecipient(Order order) throws MessagingException;
}
