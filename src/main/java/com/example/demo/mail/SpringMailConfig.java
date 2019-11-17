package com.example.demo.mail;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;


@Configuration
public class SpringMailConfig {

    private static final String EMAIL_TEMPLATE_ENCODING = StandardCharsets.UTF_8.name();

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

//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setEnvironment(final Environment environment) {
//        this.environment = environment;
//    }
//
//    @Override
//    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

//    @Bean
//    public JavaMailSender mailSender()
//    {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setProtocol("smtp");
//        mailSender.setUsername("my.gmail@gmail.com");
//        mailSender.setPassword("password");

////      JavaMail-specific mail sender configuration, based on mailserver.properties
//        final Properties javaMailProperties = new Properties();
//        try {
//            javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//        mailSender.setJavaMailProperties(javaMailProperties);
//
//        // SECOND VARIANT
////        Properties props = mailSender.getJavaMailProperties();
////        props.put("mail.transport.protocol", "smtp");
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.debug", "true");
////        props.put("mail.smtp.quitwait", "false");
////        props.put("mail.test-connection", "false");
////        mailSender.setJavaMailProperties(props);
//
//        return mailSender;
//    }

