package com.library.library.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AuthorizationCodeRepository
        extends PagingAndSortingRepository<AuthorizationCode, Long>, CrudRepository<AuthorizationCode, Long> {
    Optional<AuthorizationCode> findByRandomCodeAndMemberId(String randomCode, Long memberId);
}
