package ru.kardo.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kardo.model.User;
import ru.kardo.repo.UserRepo;

@Service
@RequiredArgsConstructor
public class MyUserDataDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email: " + email + "not found"));
        return new MyUserDataPrincipal(user);
    }
}
