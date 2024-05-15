package com.app.movie.service;

import com.app.movie.domain.Users;
import com.app.movie.model.AuthenticationResponse;
import com.app.movie.model.SignInRequest;
import com.app.movie.model.SignUpRequest;
import com.app.movie.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;



    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager manager) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.manager = manager;
    }


    public AuthenticationResponse saveUser(SignUpRequest signUpRequest) {

        if(usersRepository.existsByEmail(signUpRequest.email())){
            throw new EmailAlreadyUsedException();
        }

        Users users = new Users();
        users.email(signUpRequest.email());
        users.password(passwordEncoder.encode(signUpRequest.password()));
        users.username(signUpRequest.username());
        usersRepository.save(users);

        log.debug("Saved user: {}", users);
        return new AuthenticationResponse(
                jwtService.generateToken(users)
        );

    }

    public AuthenticationResponse signIn(SignInRequest signInRequest) {

        Users users =
                usersRepository.findByEmail(signInRequest.email())
                        .orElseThrow(EmailNotUsedException::new);

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

       log.debug("Authenticated user: {}", users);
        return new AuthenticationResponse(
                jwtService.generateToken(users)
        );


    }
}
