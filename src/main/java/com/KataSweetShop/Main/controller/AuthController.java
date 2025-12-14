package com.KataSweetShop.Main.controller;

import com.KataSweetShop.Main.dto.AuthResponse;
import com.KataSweetShop.Main.dto.LoginRequest;
import com.KataSweetShop.Main.dto.RegisterRequest;
import com.KataSweetShop.Main.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService svc;
    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @PostMapping("/register")
    public ResponseEntity<Void>register(@Valid @RequestBody RegisterRequest req){
        svc.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@Valid @RequestBody LoginRequest req){
        svc.login(req);
        return ResponseEntity.ok(svc.login(req));

    }
}
