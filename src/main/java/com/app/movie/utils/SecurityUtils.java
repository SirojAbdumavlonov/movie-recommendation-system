package com.app.movie.utils;

import com.app.movie.domain.User;
import com.app.movie.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

public class SecurityUtils {

    public static String getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getDetails();
        User principal = (User) authentication.getPrincipal();
        System.out.println(principal);
        return "Bearer ";
    }
}
