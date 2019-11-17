package com.example.demo.dao;

import com.example.demo.domain.entity.shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Product getById(Long id);

    List<Product> findAllByEnabledFalseOrderByProductName();

    Product findByProductName(String name);

    Page<Product> findAllByEnabledTrueOrderByProductName(Pageable pageable);

}
