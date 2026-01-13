package com.brian.newfriday.mappers;

import com.brian.newfriday.dtos.UserDto;
import com.brian.newfriday.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
