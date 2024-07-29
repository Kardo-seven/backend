package ru.kardo.service;

import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;

public interface UserRequestService {

   UserRequestDtoResponse postUserRequest(Long userId, Long eventId, UserRequestDtoRequest userRequestDtoRequest);
}
