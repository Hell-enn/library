package com.library.library.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberController {
    private final MemberService memberServiceImpl;

    @PostMapping
    public ResponseEntity<Object> signInMember(@Valid @RequestBody MemberDto memberDto) {
        log.debug("Request for new member sing in received!\n{}", memberDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberServiceImpl.signInMember(memberDto));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> logInMember(@NotNull @PathVariable String email) {
        log.debug("Request for member log in received!\n{}", email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberServiceImpl.logInMember(email));
    }

    @GetMapping("/{memberId}/{code}")
    public ResponseEntity<Object> authorizeMember(@NotNull @PathVariable Long memberId,
                                                  @NotNull @PathVariable String code) {
        log.debug("Request for member with id = {} authorization by code = \"{}\" received!", memberId, code);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberServiceImpl.authorizeMember(code, memberId));
    }
}