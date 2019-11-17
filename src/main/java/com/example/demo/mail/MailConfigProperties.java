package com.example.demo.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="mail")
public class MailConfigProperties {

  private String from;

  private Register register = new Register();
  private Order order = new Order();

  @Getter
  @Setter
  public class Register{
    private String subject;
    private String message;
  }

  @Getter
  @Setter
  public class Order{
    private String [] subject;
    private String [] message;
  }

}
