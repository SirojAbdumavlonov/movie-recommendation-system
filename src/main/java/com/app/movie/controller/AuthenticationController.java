package com.app.movie.controller;

import com.app.movie.dto.AuthenticationResponse;
import com.app.movie.dto.SignInRequest;
import com.app.movie.dto.SignUpRequest;
import com.app.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        log.info("Registering new user: {}", signUpRequest);
        AuthenticationResponse authenticationResponse = userService.save(signUpRequest);
        log.info("Registered successfully!");
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        log.info("Signing in user: {}", signInRequest);
        AuthenticationResponse authenticationResponse = userService.signIn(signInRequest);
        log.info("Signed in successfully!");
        return ResponseEntity.ok(authenticationResponse);
    }
}
