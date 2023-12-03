package com.responseor.ootw.controller;

import com.responseor.ootw.config.jwt.JwtTokenProvider;
import com.responseor.ootw.dto.member.MemberClotheRequestDto;
import com.responseor.ootw.dto.member.MemberJoinRequestDto;
import com.responseor.ootw.dto.member.MemberPasswordUpdateRequestDto;
import com.responseor.ootw.dto.member.MemberUpdateRequestDto;
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
    public ResponseEntity<?> memberInfoUpdate(HttpServletRequest request
    , @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        String token = jwtTokenProvider.resolveToken(request);
        Long uuid = Long.valueOf(jwtTokenProvider.getUserPk(token));

        memberService.updateMemberInfo(uuid, memberUpdateRequestDto);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/my-info/password-update")
    public ResponseEntity<?> updateMemberPassword(HttpServletRequest request
    , @RequestBody MemberPasswordUpdateRequestDto memberPasswordUpdateRequestDto) {
        String token = jwtTokenProvider.resolveToken(request);
        Long uuid = Long.valueOf(jwtTokenProvider.getUserPk(token));

        memberService.updateMemberPassword(uuid, memberPasswordUpdateRequestDto);

        return ResponseEntity.ok(null);
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

    @GetMapping("/my-clothes/{id}/delete")
    public ResponseEntity<?> deleteMemberClothes(@PathVariable("id") Long id) {
        memberService.deleteMemberClothes(id);

        return ResponseEntity.ok(null);
    }
}
