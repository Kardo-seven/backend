package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.UserRequestMapper;
import ru.kardo.model.Event;
import ru.kardo.model.Link;
import ru.kardo.model.Profile;
import ru.kardo.model.UserRequest;
import ru.kardo.repo.EventRepo;
import ru.kardo.repo.ProfileRepo;
import ru.kardo.repo.UserRequestRepo;

import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserRequestServiceImpl implements UserRequestService {

    private final ProfileRepo profileRepo;
    private final EventRepo eventRepo;
    private final UserRequestRepo userRequestRepo;
    private final UserRequestMapper userRequestMapper;

    @Override
    public UserRequestDtoResponse postUserRequest(Long userId, Long eventId, UserRequestDtoRequest userRequestDtoRequest) {
        if (userRequestRepo.findUserRequestIdByEventId(eventId) == null) {
            Profile profile = profileRepo.findById(userId).orElseThrow(() ->
                    new NotFoundValidationException("Profile with id: " + userId + " not found"));
            Event event = eventRepo.findById(eventId).orElseThrow(() ->
                    new NotFoundValidationException("Event with id: " + eventId + " not found"));
            UserRequest userRequest = UserRequest.builder()
                    .profile(profile)
                    .name(userRequestDtoRequest.getName())
                    .lastName(userRequestDtoRequest.getLastName())
                    .surName(userRequestDtoRequest.getSurName())
                    .phone(userRequestDtoRequest.getPhone())
                    .email(userRequestDtoRequest.getEmail())
                    .birthday(userRequestDtoRequest.getBirthday())
                    .linkSet(new HashSet<>())
                    .event(event)
                    .build();
            if (userRequestDtoRequest.getLinkList() == null) {
                userRequestDtoRequest.setLinkList(new ArrayList<>());
            }
            userRequestDtoRequest.getLinkList().forEach(link -> userRequest.getLinkSet().add(new Link(link)));
            userRequestRepo.save(userRequest);
            return userRequestMapper.toUserRequestDtoResponse(userRequest);
        } else {
            throw new ConflictException("User already registered on event with id: " + eventId);
        }
    }
}
