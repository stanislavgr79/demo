package com.example.demo.domain.entity.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

//!!
@Getter
@Setter
@Entity
@Table(name = "product")
@NoArgsConstructor
@EqualsAndHashCode(exclude = "description")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String description;
    private double productPrice;

}
