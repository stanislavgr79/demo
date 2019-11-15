package com.example.demo.mail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;


@Configuration
@PropertySource("classpath:mail/javamail.properties")
@PropertySource("classpath:mail/mailserver.properties")
public class SpringMailConfig implements EnvironmentAware, ApplicationContextAware {

    private static final String EMAIL_TEMPLATE_ENCODING = StandardCharsets.UTF_8.name();

    private static final String HOST = "mail.server.host";
    private static final String PORT = "mail.server.port";
    private static final String PROTOCOL = "mail.server.protocol";
    private static final String USERNAME = "mail.server.username";
    private static final String PASSWORD = "mail.server.password";

    private static final String JAVA_MAIL_FILE = "classpath:mail/mailserver.properties";

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public JavaMailSender mailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getProperty(HOST));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty(PORT))));
        mailSender.setProtocol(environment.getProperty(PROTOCOL));
        mailSender.setUsername(environment.getProperty(USERNAME));
        mailSender.setPassword(environment.getProperty(PASSWORD));

//      JavaMail-specific mail sender configuration, based on mailserver.properties
        final Properties javaMailProperties = new Properties();
        try {
            javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
        } catch (IOException e) {
            System.err.println(e);
        }
        mailSender.setJavaMailProperties(javaMailProperties);

        // SECOND VARIANT
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.smtp.quitwait", "false");
//        props.put("mail.test-connection", "false");
//        mailSender.setJavaMailProperties(props);

        return mailSender;
    }


    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){
        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
        emailTemplateResolver.setPrefix("classpath:/mail/html/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        return emailTemplateResolver;
    }

}