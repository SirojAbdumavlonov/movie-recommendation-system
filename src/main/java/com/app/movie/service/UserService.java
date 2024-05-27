package com.app.movie.service;

import com.app.movie.constant.Role;
import com.app.movie.domain.User;
import com.app.movie.dto.AuthenticationResponse;
import com.app.movie.dto.SignInRequest;
import com.app.movie.dto.SignUpRequest;
import com.app.movie.repository.UserRepository;
import com.app.movie.service.exceptions.EmailAlreadyUsedException;
import com.app.movie.service.exceptions.UserNotFoundException;
import com.app.movie.service.exceptions.UsernameAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse save(SignUpRequest signUpRequest) {
        log.debug("Saving user: {}", signUpRequest);

        checkIfEmailExists(signUpRequest.email());

        checkIfUsernameExists(signUpRequest.username());

        User user = createUser(signUpRequest);

        userRepository.save(user);

        log.debug("Saved user: {}", user);
        return createAuthenticationResponse(user);
    }

    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        log.debug("Signing in user: {}", signInRequest);

        User user = userRepository.findByEmail(signInRequest.email())
            .orElseThrow(() -> new UserNotFoundException(signInRequest.email()));

        authenticateUser(signInRequest);

        log.debug("Authenticated user: {}", user);
        return createAuthenticationResponse(user);
    }

    private User createUser(SignUpRequest signUpRequest) {
        return new User()
            .setRole(Role.ROLE_USER)
            .setEmail(signUpRequest.email())
            .setUsername(signUpRequest.username())
            .setPassword(passwordEncoder.encode(signUpRequest.password()));
    }

    private AuthenticationResponse createAuthenticationResponse(User user) {
        Map<String, Object> claims = getClaimsFromUser(user);
        String token = jwtService.generateToken(claims, user);
        return new AuthenticationResponse(token);
    }

    private void authenticateUser(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signInRequest.username(),
                signInRequest.password()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private Map<String, Object> getClaimsFromUser(User user) {
        log.debug("Getting claims from user: {}", user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());

        log.debug("Claims: UserId={}, Username={}", user.getId(), user.getUsername());
        return claims;
    }

    private void checkIfEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            log.error("Email already exists: {}", email);
            throw new EmailAlreadyUsedException(email);
        }
    }

    private void checkIfUsernameExists(String username) {
        if (userRepository.existsByEmail(username)) {
            log.error("Username already exists: {}", username);
            throw new UsernameAlreadyUsedException(username);
        }
    }
}
