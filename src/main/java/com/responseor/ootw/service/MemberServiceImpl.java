package com.responseor.ootw.service;

import com.responseor.ootw.config.jwt.JwtTokenProvider;
import com.responseor.ootw.dto.MemberJoinRequestDto;
import com.responseor.ootw.dto.Role;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;
import com.responseor.ootw.repository.ClothesByTempRepository;
import com.responseor.ootw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final ClothesByTempRepository clothesByTempRepository;

    @Transactional
    @Override
    public Long join(MemberJoinRequestDto memberJoinRequestDto) {
        if (memberRepository.findByEmail(memberJoinRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
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
    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = member.getRoles();

        return jwtTokenProvider.generateToken(member.getUuid(), roles);

    }

    @Override
    public Member getMemberInfo(Long uuid) {
        return memberRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }

    @Override
    public List<ClothesByTemp> getMemberClothes(Long uuid) {
        return clothesByTempRepository.findAllByUuid(uuid);
    }
}
