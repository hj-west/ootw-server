package com.responseor.ootw.controller;

import com.responseor.ootw.dto.member.MemberLoginRequestDto;
import com.responseor.ootw.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto loginDto) {
        String token = authService.login(loginDto.getEmail(), loginDto.getPassword());

        return ResponseEntity.ok().body(token);
    }
}
