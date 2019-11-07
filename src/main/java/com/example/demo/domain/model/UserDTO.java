package com.example.demo.domain.model;

import com.example.demo.domain.entity.person.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    private String email;
    private String password;

    private Role role;

    private List<Role> roleList =new ArrayList<>();

    private List<String> stringList = new ArrayList<>();

    Set<Role> roleSet = new HashSet<>();
}
