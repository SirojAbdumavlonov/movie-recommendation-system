package com.app.movie.controller;

import com.app.movie.model.AuthenticationResponse;
import com.app.movie.model.SignInRequest;
import com.app.movie.model.SignUpRequest;
import com.app.movie.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsersService userService;


    //todo: Function is tested and works
    //    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {

        AuthenticationResponse authenticationResponse =
                userService.saveUser(signUpRequest);

        log.info("Registered successfully!");
        //todo: registering with unique email
        // already used id is working
        // other fillings are checked by client side
        return ResponseEntity.ok(authenticationResponse);
    }
    //todo: Function is tested and works
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {

       AuthenticationResponse authenticationResponse =
                userService.signIn(signInRequest);

        log.info("Signed in successfully!");
        //todo: signing in only with email and password
        // trying in both ways(if email or password is incorrect)
        // incorrect password is working
        // incorrect email is working
        return ResponseEntity.ok(authenticationResponse);
    }

}
