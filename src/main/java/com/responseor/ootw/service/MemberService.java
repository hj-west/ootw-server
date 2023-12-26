package com.responseor.ootw.service;

import com.responseor.ootw.dto.member.MemberClotheRequestDto;
import com.responseor.ootw.dto.member.MemberJoinRequestDto;
import com.responseor.ootw.dto.member.MemberPasswordUpdateRequestDto;
import com.responseor.ootw.dto.member.MemberUpdateRequestDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.entity.Member;

import java.util.List;

public interface MemberService {
    Long join(MemberJoinRequestDto memberJoinRequestDto);
    Member getMemberInfo(Long uuid);
    void updateMemberInfo(Long uuid, MemberUpdateRequestDto memberUpdateRequestDto);
    void updateMemberPassword(Long uuid, MemberPasswordUpdateRequestDto memberPasswordUpdateRequestDto);

    List<ClothesByTemp> getMemberClothes(Long uuid);
    void addMemberClothes(Long uuid, List<MemberClotheRequestDto> memberClotheRequestDtoList);
    void deleteMemberClothes(int id, Long uuid);
}
