package com.responseor.ootw.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberLoginRequestDto {
    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
