package com.psy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;

    public UserAuth getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
    }
}
