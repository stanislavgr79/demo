package com.example.demo.domain.entity.person;

import com.example.demo.domain.entity.shop.Order;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = {"firstName", "lastName", "user"})
@ToString(exclude = {"address", "ordersList"})
@NamedEntityGraph(name = "Customer.user",
        attributeNodes = @NamedAttributeNode("user"))
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "customer")
    private List<Order> ordersList = new ArrayList<>();

}