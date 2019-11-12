package com.example.demo.dao.repository;

import com.example.demo.domain.entity.shop.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product getById(Long id);

    List<Product> findAllByEnabledTrueOrderByProductName();

    List<Product> findAllByEnabledFalseOrderByProductName();

    Product findByProductName(String name);
}
