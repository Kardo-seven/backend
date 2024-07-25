package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kardo.dto.UserDtoRequest;
import ru.kardo.dto.UserDtoResponse;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.UserMapper;
import ru.kardo.model.Authority;
import ru.kardo.model.User;
import ru.kardo.repo.UserRepo;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDtoResponse addUser(UserDtoRequest userDtoRequest) {
        emailValidation(userDtoRequest.getEmail());
        User user = User.builder()
               .email(userDtoRequest.getEmail())
               .password(passwordEncoder.encode(userDtoRequest.getPassword()))
               .authoritySet(new HashSet<>())
               .build();
        user.getAuthoritySet().add(new Authority(userDtoRequest.getEnumAuth()));
        userRepo.save(user);
        return userMapper.toUserDtoResponse(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() ->
                new NotFoundValidationException("User with email " + email + " not found"));
    }

    private void emailValidation(String email) {
        Set<String> emailsSet = new HashSet<>(userRepo.findAllEmails());
        if (emailsSet.contains(email)) {
            throw new ConflictException("Email: " + email + ", already used");
        }
    }
}
