package com.example.demo.security;

import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);

        if (user == null) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        System.out.println("========");
        System.out.println("User detail say: i am Found User: " + user.getEmail());


        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role: user.getRoles()){
            System.out.println("UserDetailservice say: do get from User set<Role> any role - i find current role :" + role.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            System.out.println("to Set<GrantedAuthority> add : new SimpleGrantedAuthority(role.getName())");
        }

        System.out.println("view Set<GrantedAuthority> : ");

        grantedAuthorities.forEach(o-> System.out.println("this roles add to set: " + o.getAuthority()));
        UserDetails userDetails = new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), grantedAuthorities);

        System.out.println("from UserDetailsServiceImpl implements UserDetailsService : i return class UserDetails:");
        System.out.println(userDetails.getUsername());
        userDetails.getAuthorities().stream().forEach(o-> System.out.println(((GrantedAuthority) o).getAuthority()));
        System.out.println("=======");

        return userDetails;


    }

}