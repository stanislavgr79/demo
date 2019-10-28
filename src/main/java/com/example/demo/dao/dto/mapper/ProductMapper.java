package com.example.demo.dao.dto.mapper;

import com.example.demo.dao.dto.ProductDTO;
import com.example.demo.domain.entity.shop.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDTO productToDTO(Product product);
    List<ProductDTO> productsToListDTO(List<Product> products);
}
