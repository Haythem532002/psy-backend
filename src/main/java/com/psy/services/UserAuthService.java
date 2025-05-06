package com.psy.services;

import com.psy.user.UserAuth;
import com.psy.user.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;

    public Integer getUserIdByEmail(String email) {
        var user = userAuthRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User Auth Not Found")
        );
        return user.getId();
    }

    public UserAuth getUserById(Integer id) {
        return userAuthRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
    }
}
