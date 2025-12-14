package com.KataSweetShop.Main.service;

import com.KataSweetShop.Main.dto.AuthResponse;
import com.KataSweetShop.Main.dto.LoginRequest;
import com.KataSweetShop.Main.dto.RegisterRequest;
import com.KataSweetShop.Main.entity.User;
import com.KataSweetShop.Main.exception.ConflictException;
import com.KataSweetShop.Main.exception.UnauthorizedException;
import com.KataSweetShop.Main.repository.UserRepository;
import com.KataSweetShop.Main.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    //Register

    public void register(RegisterRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ConflictException("Username already exists");
        }

        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole() == null ? "ROLE_USER" : req.getRole())
                .build();

        userRepository.save(user);
    }

    //LOGIN

    public AuthResponse login(LoginRequest req) {

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = jwtUtils.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new AuthResponse(token);
    }
}
