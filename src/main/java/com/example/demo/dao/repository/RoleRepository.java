package com.example.demo.dao.repository;

import com.example.demo.domain.entity.person.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role getById(Long id);
}
