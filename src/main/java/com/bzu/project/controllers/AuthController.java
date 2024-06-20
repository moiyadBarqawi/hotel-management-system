package com.bzu.project.controllers;

import com.bzu.project.model.User;
import com.bzu.project.request.AuthenticationRequest;
import com.bzu.project.request.RegisterRequest;
import com.bzu.project.response.AuthenticationResponse;
import com.bzu.project.security.JwtTokenProvider;
import com.bzu.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        User updatedUser = userService.updatePassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }
}
