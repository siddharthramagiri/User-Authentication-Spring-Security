package com.authorize.userauthentication.controller;

import com.authorize.userauthentication.dto.authDto.AuthRequestDto;
import com.authorize.userauthentication.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        String username = request.email();

        return authService.login(username, request.password());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AuthRequestDto request) {
        String username = request.email();
        return authService.signup(username, request.password());
    }
}
