package com.example.demo.dao.repository;

import com.example.demo.domain.entity.person.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmail(String email);

    boolean existsByEmail(String email);
}
