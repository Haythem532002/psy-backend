package com.psy.services;

import com.psy.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Integer getUserIdByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
        return user.getId();
    }
}
