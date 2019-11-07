package com.example.demo.domain.entity.shop;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "product")
@NoArgsConstructor
@EqualsAndHashCode(of = {"productName"})
@ToString(exclude = {"description", "productPrice"})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String description;
    private double productPrice;

    private boolean enabled = true;
}
