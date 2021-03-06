package com.example.demo.dao;

import com.example.demo.domain.entity.person.Role;
import com.example.demo.domain.entity.person.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmail(String email);

    User getById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET enabled =?2, accountNonExpired =?3 , credentialsNonExpired =?4, accountNonLocked =?5 WHERE id=?1", nativeQuery = true)
    void  updateUserStatus(Long id, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked);

    List<User> findAllByRolesIsNotContainingOrderByEmail(Role role);

}