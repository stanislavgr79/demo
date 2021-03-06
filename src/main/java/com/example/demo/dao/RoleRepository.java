package com.example.demo.dao;

import com.example.demo.domain.entity.person.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role getById(Long id);
}
