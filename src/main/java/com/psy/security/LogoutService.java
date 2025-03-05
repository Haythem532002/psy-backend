package com.psy.security;

import com.psy.token.Token;
import com.psy.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String header = request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")) return;
        String jwtToken = header.substring(7);
        Token token = tokenRepository.findByToken(jwtToken).orElse(null);
        if(token!=null) tokenRepository.deleteById(token.getId());
    }
}
