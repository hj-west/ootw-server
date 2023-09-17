package com.responseor.ootw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class MemberJoinRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String telNo;


    private Role role;
}
