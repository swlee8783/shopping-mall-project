package com.example.shoppingmallwebsiteproject.controller;

import com.example.shoppingmallwebsiteproject.repository.UserLoginRequest;
import com.example.shoppingmallwebsiteproject.repository.UserLoginResponse;
import com.example.shoppingmallwebsiteproject.repository.UserSignupRequest;
import com.example.shoppingmallwebsiteproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserSignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UserLoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}