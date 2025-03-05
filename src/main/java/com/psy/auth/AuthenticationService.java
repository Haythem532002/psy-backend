package com.psy.auth;


import com.psy.security.JwtService;
import com.psy.token.Token;
import com.psy.token.TokenRepository;
import com.psy.user.RoleEnum;
import com.psy.user.User;
import com.psy.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.Claims;
import java.util.HashMap;

import static com.psy.token.TokenType.BEARER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;

    public Integer register(RegistrationRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEnum.USER)
                .enabled(true)
                .locked(false)
                .build();
        return userRepository.save(user).getId();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //use the authentication manager to authenticate the user with UsernamePasswordAuthenticationToken
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = (User) auth.getPrincipal();
        var claims = new HashMap<String, Object>();
        // generate a jwt access-token and refresh-token
        String token = jwtService.generateToken(claims, user);
        String refreshToken = jwtService.generateRefreshToken(user);
        var tokenRepo = Token.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(user)
                .tokenType(BEARER)
                .build();
        tokenRepository.save(tokenRepo);
        //return the token
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build()
                ;
    }

    public boolean validate(ValidateRequest request) {
        String token = request.getToken();
        Claims claims = jwtService.extractAllClaims(token);

        if (claims == null) {
            return false;
        }

        String username = jwtService.extractUsername(token);
        if (username != null) {
            var user = userDetailsService.loadUserByUsername(username);
            return jwtService.isTokenValid(token, user);
        }
        return false;
    }



    public AuthenticationResponse refresh(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        Token isTokenPresent = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token not found"));

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired");
        }

        var accessToken = jwtService.generateToken(new HashMap<>(), userDetails);
        isTokenPresent.setToken(accessToken);
        tokenRepository.save(isTokenPresent);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}