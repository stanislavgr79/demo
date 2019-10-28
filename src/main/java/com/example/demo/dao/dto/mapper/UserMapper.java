package com.example.demo.dao.dto.mapper;

import com.example.demo.dao.dto.UserDTO;
import com.example.demo.domain.entity.person.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper{

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToDTO(User user);

}
