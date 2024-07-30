package ru.kardo.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.request.RequestPreviewDtoResponse;
import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;

import java.io.IOException;

public interface UserRequestService {

   UserRequestDtoResponse postUserRequest(Long userId, Long eventId, UserRequestDtoRequest userRequestDtoRequest);

   RequestPreviewDtoResponse uploadRequestPreview(Long userId, Long eventId, MultipartFile multipartFile) throws IOException;

   UserRequestDtoResponse patchUserRequest(Long userId, Long requestId, UserRequestDtoRequest userRequestDtoRequest);
}
