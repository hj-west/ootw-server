package com.responseor.ootw.controller;

import com.responseor.ootw.dto.CustomUserDetails;
import com.responseor.ootw.dto.MemberJoinRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;
import com.responseor.ootw.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Long> join(@Valid @RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        return ResponseEntity.ok().body(memberService.join(memberJoinRequestDto));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Member> memberInfo(@PathVariable("uuid") Long uuid) {
        return ResponseEntity.ok().body(memberService.getMemberInfo(uuid));
    }

    @GetMapping("/{uuid}/clothes")
    public ResponseEntity<List<ClothesByTemp>> memberClothes(@PathVariable("uuid") Long uuid) {
        return ResponseEntity.ok().body(memberService.getMemberClothes(uuid));
    }
}
