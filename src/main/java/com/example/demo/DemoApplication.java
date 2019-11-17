package com.example.demo;

import com.example.demo.mail.MailConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(MailConfigProperties.class)
public class DemoApplication implements WebMvcConfigurer {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

//		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//		EmailService mailService = context.getBean(EmailService.class);
//		mailService.print();
	}

}
