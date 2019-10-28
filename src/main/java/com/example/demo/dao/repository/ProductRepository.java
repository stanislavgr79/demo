package com.example.demo.dao.repository;

import com.example.demo.domain.entity.shop.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product getById(Long id);

    @Modifying
    @Query(value = "UPDATE product SET productName =?2" +
            " AND productPrice=?3 AND description =?4 WHERE id=?1", nativeQuery = true)
    void  updateProduct(Long id, String name, double price, String description );
}
