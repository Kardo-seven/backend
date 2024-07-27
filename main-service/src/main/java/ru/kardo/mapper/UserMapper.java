package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.user.UserDtoResponse;
import ru.kardo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "authoritySet", source = "authoritySet")
    UserDtoResponse toUserDtoResponse(User user);
}
