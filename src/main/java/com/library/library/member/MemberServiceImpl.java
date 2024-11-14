package com.library.library.member;

import com.library.library.email.EmailCreationService;
import com.library.library.exceptions.BadRequestException;
import com.library.library.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthorizationCodeRepository authorizationCodeRepository;
    private final MemberMapper memberMapper;
    private final EmailCreationService emailCreationServiceImpl;

    @Override
    public MemberDto signInMember(MemberDto memberDto) {

        log.debug("Request for signing in member received \n{}", memberDto);

        String email = memberDto.getEmail();
        if(email == null)
            throw new BadRequestException("There is no email!");

        String phoneNumber = memberDto.getPhoneNumber();

        Optional<Member> existingMember = memberRepository.findByEmailOrPhoneNumber(email, phoneNumber);
        if(existingMember.isPresent())
            throw new BadRequestException("Member with such email or phone number already exists!");

        Member newMember = memberMapper.memberDtoToMemberEntity(memberDto);
        MemberDto addedMember = memberMapper.memberEntityToMemberDto(memberRepository.save(newMember));

        log.debug("Returning added member \n{}", addedMember);
        return addedMember;
    }

    @Override
    public MemberDto logInMember(String email) {

        log.debug("Request for logging in member received \n{}", email);

        Member existingMember = memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("There is no such member with email " + email + "!"));

        String code = emailCreationServiceImpl.createLogInMail(email);
        authorizationCodeRepository.save(
                AuthorizationCode
                        .builder()
                        .member(existingMember)
                        .randomCode(code)
                        .build());

        MemberDto memberDto = memberMapper.memberEntityToMemberDto(existingMember);
        log.debug("Returning existing member \n{}", memberDto);
        return memberDto;
    }

    @Override
    public MemberDto authorizeMember(String code, Long memberId) {

        log.debug("Request for authorizing member with id = {} received", memberId);

        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("There is no such member with id = " + memberId + "!"));

        AuthorizationCode authorizationCode =
                authorizationCodeRepository
                        .findByRandomCodeAndMemberId(code, memberId)
                        .orElseThrow(() -> new NotFoundException("There is no such code " + code +
                                                                 " for member with id = " + memberId + "!"));

        authorizationCodeRepository.delete(authorizationCode);
        MemberDto memberDto = memberMapper.memberEntityToMemberDto(member);

        log.debug("Returning existing member \n{}", memberDto);

        return memberDto;
    }
}