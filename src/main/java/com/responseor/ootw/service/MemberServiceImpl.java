package com.responseor.ootw.service;

import com.responseor.ootw.config.exception.CustomException;
import com.responseor.ootw.config.exception.ErrorCode;
import com.responseor.ootw.config.jwt.JwtTokenProvider;
import com.responseor.ootw.dto.Role;
import com.responseor.ootw.dto.member.MemberClotheRequestDto;
import com.responseor.ootw.dto.member.MemberJoinRequestDto;
import com.responseor.ootw.dto.member.MemberPasswordUpdateRequestDto;
import com.responseor.ootw.dto.member.MemberUpdateRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;
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
    @Transactional
    public void updateMemberInfo(Long uuid, MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_MEMBER_INFORMATION));

        member.setTelNo(memberUpdateRequestDto.getTelNo());
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void updateMemberPassword(Long uuid, MemberPasswordUpdateRequestDto memberPasswordUpdateRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_MEMBER_INFORMATION));

        if (memberPasswordUpdateRequestDto.getNewPassword().equals(memberPasswordUpdateRequestDto.getConfirmPassword())) {
            member.setPassword(memberPasswordUpdateRequestDto.getNewPassword());

            memberRepository.save(member);
        }

    }

    @Override
    public List<ClothesByTemp> getMemberClothes(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long uuid = Long.valueOf(jwtTokenProvider.getUserPk(token));

        return clothesByTempRepository.findAllByUuid(uuid);
    }

    @Override
    public void addMemberClothes(Long uuid, List<MemberClotheRequestDto> memberClotheRequestDtoList) {
        for (MemberClotheRequestDto memberClotheRequestDto : memberClotheRequestDtoList) {
            ClothesByTemp clothesByTemp = ClothesByTemp.builder()
                    .clothes(memberClotheRequestDto.getClothes())
                    .startTemp(memberClotheRequestDto.getStartTemp())
                    .endTemp(memberClotheRequestDto.getEndTemp())
                    .uuid(uuid)
                    .build();

            clothesByTempRepository.save(clothesByTemp);
        }
    }

    @Override
    @Transactional
    public void deleteMemberClothes(int id) {
        ClothesByTemp clothesByTemp = clothesByTempRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CLOTHES));

        clothesByTempRepository.delete(clothesByTemp);
    }
}
