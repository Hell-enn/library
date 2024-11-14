package com.library.library.member;

import com.library.library.member.MemberDto;

public interface MemberService {
    MemberDto signInMember(MemberDto memberDto);
    MemberDto logInMember(String email);
    MemberDto authorizeMember(String code, Long memberId);
}