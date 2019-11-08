package com.example.demo.domain.model;

import com.example.demo.domain.entity.person.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = "email")
@ToString(exclude = "roleSet")
public class UserDTO {

    private String email;
    private String password;

    @Builder.Default
    Set<Role> roleSet = new HashSet<>();
}
