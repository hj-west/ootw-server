package com.responseor.ootw.service;

import com.responseor.ootw.dto.MemberJoinRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;

import java.util.List;

public interface MemberService {

    Long join(MemberJoinRequestDto memberJoinRequestDto);
    String login(String email, String password);
    Member getMemberInfo(Long uuid);
    List<ClothesByTemp> getMemberClothes(Long uuid);
}
