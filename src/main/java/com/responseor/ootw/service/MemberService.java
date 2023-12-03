package com.responseor.ootw.service;

import com.responseor.ootw.dto.member.MemberClotheRequestDto;
import com.responseor.ootw.dto.member.MemberJoinRequestDto;
import com.responseor.ootw.dto.member.MemberUpdateRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MemberService {
    Long join(MemberJoinRequestDto memberJoinRequestDto);
    Member getMemberInfo(HttpServletRequest request);
    void updateMemberInfo(Long uuid, MemberUpdateRequestDto memberUpdateRequestDto);
    List<ClothesByTemp> getMemberClothes(HttpServletRequest request);
    void addMemberClothes(Long uuid, List<MemberClotheRequestDto> memberClotheRequestDtoList);
}
