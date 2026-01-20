package com.brian.newfriday.mappers;

import com.brian.newfriday.dtos.RegisterUserRequest;
import com.brian.newfriday.dtos.UpdateUserRequest;
import com.brian.newfriday.dtos.UserDto;
import com.brian.newfriday.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest userRequest);

    User update(UpdateUserRequest userRequest, @MappingTarget User user);
}
