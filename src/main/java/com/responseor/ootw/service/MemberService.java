package com.responseor.ootw.service;

import com.responseor.ootw.dto.MemberJoinRequestDto;

public interface MemberService {

    Long join(MemberJoinRequestDto memberJoinRequestDto);
    String login(String email, String password);
}
