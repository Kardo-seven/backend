package ru.kardo.service;

import ru.kardo.dto.UserDtoRequest;
import ru.kardo.dto.UserDtoResponse;
import ru.kardo.model.User;

public interface UserService {

    UserDtoResponse addUser(UserDtoRequest userDtoRequest);

    User findUserByEmail(String email);
}
