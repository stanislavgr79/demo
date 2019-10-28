package com.example.demo.dao.dto.mapper;

import com.example.demo.dao.dto.CustomerDTO;
import com.example.demo.domain.entity.person.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO customerToDTO(Customer customer);
}
