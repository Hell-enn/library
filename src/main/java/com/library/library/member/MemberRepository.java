package com.library.library.member;

import com.library.library.member.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MemberRepository extends PagingAndSortingRepository<Member, Long>, CrudRepository<Member, Long> {
    Optional<Member> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<Member> findByEmail(String email);
}