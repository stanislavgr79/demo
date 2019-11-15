package com.example.demo.mail;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.demo.domain.entity.person.Customer;
import com.example.demo.domain.entity.shop.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@PropertySource("classpath:mail/message.properties")
public class EmailService implements EnvironmentAware {

    private static final String EMAIL_TEMPLATE_ENCODING = StandardCharsets.UTF_8.name();
    private static final String EMAIL_NAME_ACCOUNT_FROM = "mail.name.account.from";

    private static final String EMAIL_REGISTER_TEMPLATE_NAME = "mail.register.message";
    private static final String EMAIL_ORDER_TEMPLATE_NAME = "mail.order.message";
    private static final String EMAIL_ORDER_UPDATE_TEMPLATE_NAME = "mail.order.update.message";

    private static final String EMAIL_REGISTER_SUBJECT_NAME = "mail.register.subject";
    private static final String EMAIL_ORDER_SUBJECT_NAME = "mail.order.subject";
    private static final String EMAIL_ORDER_UPDATE_SUBJECT_NAME = "mail.order.update.subject";


    @Autowired
    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private OrderService orderService;


    public void sendRegisterMail(
            final String recipientName, final String recipientEmail, String password)
            throws MessagingException {
        Locale locale = Locale.ENGLISH;
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("pass", password);

        final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, EMAIL_TEMPLATE_ENCODING);
        message.setSubject(Objects.requireNonNull(environment.getProperty(EMAIL_REGISTER_SUBJECT_NAME)));
        message.setFrom(Objects.requireNonNull(environment.getProperty(EMAIL_NAME_ACCOUNT_FROM)));
        message.setTo(recipientEmail);

        final String htmlContent = templateEngine.process(Objects.requireNonNull(environment.getProperty(EMAIL_REGISTER_TEMPLATE_NAME)), ctx);
        message.setText(htmlContent, true);

        this.emailSender.send(mimeMessage);
    }

    public void sendOrderMailToRecipient(final Customer customer, final Order order) throws MessagingException {
        final String recipientName = customer.getFirstName() + " " + customer.getLastName();
        final String recipientEmail = customer.getUser().getEmail();
        Locale locale = Locale.ENGLISH;
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("createDate", order.getOrderCreateDate());
        ctx.setVariable("order", order);
        ctx.setVariable("orderDetail", order.getOrderDetail());

        final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, EMAIL_TEMPLATE_ENCODING);
        message.setSubject(Objects.requireNonNull(environment.getProperty(EMAIL_ORDER_SUBJECT_NAME)));
        message.setFrom(Objects.requireNonNull(environment.getProperty(EMAIL_NAME_ACCOUNT_FROM)));
        message.setTo(recipientEmail);

        final String htmlContent = templateEngine.process(Objects.requireNonNull(environment.getProperty(EMAIL_ORDER_TEMPLATE_NAME)), ctx);
        message.setText(htmlContent, true);

        this.emailSender.send(mimeMessage);
    }

    public void sendOrderUpdateStatusToRecipient(Order orderIn) throws MessagingException {
        Order order = orderService.getOrderById(orderIn.getId());
        final String recipientName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
        final String recipientEmail = order.getCustomer().getUser().getEmail();
        Locale locale = Locale.ENGLISH;
        String pattern = "dd-MM-yyyy HH:mm";
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("updateDate", order.getOrderModifyDate());
        ctx.setVariable("order", order);
        ctx.setVariable("pattern", pattern);

        final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, EMAIL_TEMPLATE_ENCODING);
        message.setSubject(Objects.requireNonNull(environment.getProperty(EMAIL_ORDER_UPDATE_SUBJECT_NAME)));
        message.setFrom(Objects.requireNonNull(environment.getProperty(EMAIL_NAME_ACCOUNT_FROM)));
        message.setTo(recipientEmail);

        final String htmlContent = templateEngine.process(Objects.requireNonNull(environment.getProperty(EMAIL_ORDER_UPDATE_TEMPLATE_NAME)), ctx);
        message.setText(htmlContent, true);

        this.emailSender.send(mimeMessage);
    }

}