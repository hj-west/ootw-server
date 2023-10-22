package com.responseor.ootw.service;

import com.responseor.ootw.config.jwt.JwtTokenProvider;
import com.responseor.ootw.dto.MemberJoinRequestDto;
import com.responseor.ootw.dto.Role;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;
import com.responseor.ootw.config.exception.CustomException;
import com.responseor.ootw.config.exception.ErrorCode;
import com.responseor.ootw.repository.ClothesByTempRepository;
import com.responseor.ootw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ClothesByTempRepository clothesByTempRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public Long join(MemberJoinRequestDto memberJoinRequestDto) {
        if (memberRepository.findByEmail(memberJoinRequestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_EXISTS);
        }
        List<String> roles = new ArrayList<>();
        roles.add(Role.USER.role());

        Member member = memberRepository.save(Member.builder()
                        .email(memberJoinRequestDto.getEmail())
                        .password(passwordEncoder.encode(memberJoinRequestDto.getPassword()))
                        .telNo(memberJoinRequestDto.getTelNo())
                .roles(roles)
                .build());

        return member.getUuid();
    }

    @Override
    public Member getMemberInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long uuid = Long.valueOf(jwtTokenProvider.getUserPk(token));

        return memberRepository.findByUuid(uuid).orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_MEMBER_INFORMATION));
    }

    @Override
    public List<ClothesByTemp> getMemberClothes(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long uuid = Long.valueOf(jwtTokenProvider.getUserPk(token));

        return clothesByTempRepository.findAllByUuid(uuid);
    }
}
