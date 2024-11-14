package com.library.library.member;

import com.library.library.member.MemberDto;
import com.library.library.member.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MemberMapper {

    public Member memberDtoToMemberEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .firstName(memberDto.getFirstName())
                .lastName(memberDto.getLastName())
                .phoneNumber(memberDto.getPhoneNumber())
                .email(memberDto.getEmail())
                .build();
    }

    public MemberDto memberEntityToMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();
    }
}