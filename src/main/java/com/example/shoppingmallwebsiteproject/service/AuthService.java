package com.example.shoppingmallwebsiteproject.service;

import com.example.shoppingmallwebsiteproject.config.JwtUtil;
import com.example.shoppingmallwebsiteproject.domain.User;
import com.example.shoppingmallwebsiteproject.dto.*;
import com.example.shoppingmallwebsiteproject.repository.UserLoginRequest;
import com.example.shoppingmallwebsiteproject.repository.UserLoginResponse;
import com.example.shoppingmallwebsiteproject.repository.UserRepository;
import com.example.shoppingmallwebsiteproject.repository.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(UserSignupRequest request) {
        boolean exists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (exists) throw new RuntimeException("이미 가입된 이메일입니다.");

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new UserLoginResponse(token);
    }
}

