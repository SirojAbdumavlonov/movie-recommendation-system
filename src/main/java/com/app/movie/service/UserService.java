package com.app.movie.service;

import com.app.movie.domain.User;
import com.app.movie.dto.AuthenticationResponse;
import com.app.movie.dto.SignInRequest;
import com.app.movie.dto.SignUpRequest;
import com.app.movie.repository.UserRepository;
import com.app.movie.service.exceptions.EmailAlreadyUsedException;
import com.app.movie.service.exceptions.EmailNotUsedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    public AuthenticationResponse saveUser(SignUpRequest signUpRequest) {

        if(userRepository.existsByEmail(signUpRequest.email())){
            throw new EmailAlreadyUsedException();
        }

        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setUsername(signUpRequest.username());
        userRepository.save(user);

        log.debug("Saved user: {}", user);
        return new AuthenticationResponse(
                jwtService.generateToken(user)
        );

    }

    public AuthenticationResponse signIn(SignInRequest signInRequest) {

        User user = userRepository.findByEmail(signInRequest.email())
                        .orElseThrow(EmailNotUsedException::new);

        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
            signInRequest.email(),
            signInRequest.password()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("Authenticated user: {}", user);
        return new AuthenticationResponse(
                jwtService.generateToken(user)
        );
    }
}
