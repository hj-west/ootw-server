package com.responseor.ootw.service;

import com.responseor.ootw.dto.auth.KakaoTokenResponseDto;

public interface AuthService {
    String login(String email, String password);
    KakaoTokenResponseDto getAccessToken(Integer code);
}
