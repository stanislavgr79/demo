package com.example.demo.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id", "confirmPassword"})
public class RegistrationForm {

    private Long id;

    private String firstName;
    private String lastName;

    private String country;
    private String city;
    private String street;
    private String flat;

    private String email;
    private String password;
    private String confirmPassword;

}
