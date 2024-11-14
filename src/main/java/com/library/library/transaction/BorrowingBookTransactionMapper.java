package com.library.library.transaction;

import com.library.library.book.BookMapper;
import com.library.library.member.MemberMapper;
import com.library.library.genre.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BorrowingBookTransactionMapper {

    private final BookMapper bookMapper;
    private final MemberMapper memberMapper;

    public BorrowingBookTransaction transactionDtoToTransactionEntity(BorrowingBookTransactionDto dto) {
        return BorrowingBookTransaction.builder()
                .id(dto.getId())
                .bookingDate(dto.getBookingDate())
                .plannedStartDate(dto.getPlannedStartDate())
                .plannedEndDate(dto.getPlannedEndDate())
                .actualReturnDate(dto.getActualReturnDate())
                .book(bookMapper.bookDtoToBookEntity(dto.getBookDto()))
                .member(memberMapper.memberDtoToMemberEntity(dto.getMemberDto()))
                .build();
    }

    public BorrowingBookTransactionDto transactionEntityToTransactionDto(BorrowingBookTransaction entity, List<GenreDto> genreDtoList) {
        return BorrowingBookTransactionDto.builder()
                .id(entity.getId())
                .bookingDate(entity.getBookingDate())
                .plannedStartDate(entity.getPlannedStartDate())
                .plannedEndDate(entity.getPlannedEndDate())
                .actualReturnDate(entity.getActualReturnDate())
                .bookDto(bookMapper.bookEntityToBookDto(entity.getBook(), genreDtoList))
                .memberDto(memberMapper.memberEntityToMemberDto(entity.getMember()))
                .build();
    }
}