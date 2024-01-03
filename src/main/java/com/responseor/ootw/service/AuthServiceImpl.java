package com.responseor.ootw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.responseor.ootw.config.exception.CustomException;
import com.responseor.ootw.config.exception.ErrorCode;
import com.responseor.ootw.config.jwt.JwtTokenProvider;
import com.responseor.ootw.dto.auth.KakaoTokenResponseDto;
import com.responseor.ootw.entity.Member;
import com.responseor.ootw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNREGISTERED_MEMBER));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INCORRECT_MEMBER_INFORMATION);
        }

        List<String> roles = member.getRoles();

        return jwtTokenProvider.generateToken(member.getUuid(), roles);

    }

    public KakaoTokenResponseDto getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + "9f69a060918ad406bdd0bc6cbd272655");
            sb.append("&code=" + code);

            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(sb.toString());
            } catch (IOException e) {
                log.error("Kakao Token Api Error : {}", e.getMessage(), e);
                throw new RuntimeException(e);
            } finally {
                if (bw != null) {
                    bw.flush();
                }
            }
            BufferedReader br = null;
            String line = "";
            String result = "";
            try {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result += line;
                }

                ObjectMapper objectMapper = new ObjectMapper();

                log.info(result);

                KakaoTokenResponseDto responseDto = objectMapper.readValue(result, KakaoTokenResponseDto.class);
                accessToken = responseDto.getAccess_token();
                return responseDto;
            } catch (IOException e) {
                log.error("Kakao Token Api Error : {}", e.getMessage(), e);
                throw new RuntimeException(e);
            } finally {
                if (br != null)
                    br.close();
            }
        } catch (IOException e) {
            log.error("Kakao Token Api Error : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
