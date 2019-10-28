package com.example.demo.domain.entity.person;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "address")
@NoArgsConstructor
@EqualsAndHashCode
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message = "*Please provide your country")
    private String country;
    @NonNull
    @NotEmpty(message = "*Please provide your city")
    private String city;
    @NonNull
    @NotEmpty(message = "*Please provide your street")
    private String street;
    @NonNull
    @NotEmpty(message = "*Please provide your flat")
    private String flat;

    @OneToOne(mappedBy = "address")
    @EqualsAndHashCode.Exclude
    private Customer customer;

}