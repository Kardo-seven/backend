package ru.kardo.service;

import ru.kardo.dto.user.UserDtoRequest;
import ru.kardo.dto.user.UserDtoResponse;
import ru.kardo.model.User;

public interface UserService {

    UserDtoResponse addUser(UserDtoRequest userDtoRequest);

    User findUserByEmail(String email);
}
