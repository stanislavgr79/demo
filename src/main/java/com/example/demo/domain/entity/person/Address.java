package com.example.demo.domain.entity.person;

import lombok.*;

import javax.persistence.*;
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

    private String country;
    private String city;
    private String street;
    private String flat;

    @OneToOne(mappedBy = "address")
    @EqualsAndHashCode.Exclude
    private Customer customer;

}