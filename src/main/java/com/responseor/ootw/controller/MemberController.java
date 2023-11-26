package com.responseor.ootw.controller;

import com.responseor.ootw.config.jwt.JwtTokenProvider;
import com.responseor.ootw.dto.member.MemberClotheRequestDto;
import com.responseor.ootw.dto.member.MemberJoinRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;
import com.responseor.ootw.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    public ResponseEntity<Long> join(@Valid @RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        return ResponseEntity.ok().body(memberService.join(memberJoinRequestDto));
    }

    @GetMapping("/my-info")
    public ResponseEntity<Member> memberInfo(HttpServletRequest request) {
        return ResponseEntity.ok().body(memberService.getMemberInfo(request));
    }

    @PostMapping("/my-info")
    public ResponseEntity<Member> memberInfoUpdate(HttpServletRequest request) {
        return ResponseEntity.ok().body(memberService.getMemberInfo(request));
    }

    @GetMapping("/my-clothes")
    public ResponseEntity<List<ClothesByTemp>> memberClothes(HttpServletRequest request) {
        return ResponseEntity.ok().body(memberService.getMemberClothes(request));
    }

    @PostMapping("/my-clothes")
    public ResponseEntity<?> addMemberClothes(HttpServletRequest request
            , @RequestBody List<MemberClotheRequestDto> memberClotheRequestDtoList) {
        String token = jwtTokenProvider.resolveToken(request);
        Long uuid = Long.valueOf(jwtTokenProvider.getUserPk(token));

        memberService.addMemberClothes(uuid, memberClotheRequestDtoList);

        return ResponseEntity.ok(null);
    }
}
