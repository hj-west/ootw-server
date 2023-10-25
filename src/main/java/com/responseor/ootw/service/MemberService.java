package com.responseor.ootw.service;

import com.responseor.ootw.dto.member.MemberJoinRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MemberService {
    Long join(MemberJoinRequestDto memberJoinRequestDto);
    Member getMemberInfo(HttpServletRequest request);
    List<ClothesByTemp> getMemberClothes(HttpServletRequest request);
}
