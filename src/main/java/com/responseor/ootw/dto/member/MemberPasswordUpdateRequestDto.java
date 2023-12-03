package com.responseor.ootw.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPasswordUpdateRequestDto {
    private String newPassword;
    private String confirmPassword;
}
