package com.example.demo.dao.dto.mapper;

import com.example.demo.dao.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;

@Mapper
@Repository
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    OrderDTO orderToDTO(Order order);
}
