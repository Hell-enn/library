package com.library.library.transaction;

import com.library.library.book.BookDto;
import com.library.library.genre.GenreDto;
import com.library.library.member.MemberDto;
import com.library.library.member.Member;
import com.library.library.exceptions.BadRequestException;
import com.library.library.exceptions.NotFoundException;
import com.library.library.book.BookMapper;
import com.library.library.genre.GenreMapper;
import com.library.library.member.MemberMapper;
import com.library.library.genre.GenreRepository;
import com.library.library.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowingBookTransactionServiceImpl implements BorrowingObjectTransactionService {

    private final BorrowingBookTransactionRepository borrowingBookTransactionRepository;
    private final MemberRepository memberRepository;
    private final GenreRepository genreRepository;
    private final BorrowingBookTransactionMapper borrowingBookTransactionMapper;
    private final BookMapper bookMapper;
    private final MemberMapper memberMapper;
    private final GenreMapper genreMapper;

    @Override
    public BorrowingBookTransactionDto borrowObject(BorrowingBookTransactionDto borrowingBookTransactionDto) {

        log.debug("Request for booking book received \n{}", borrowingBookTransactionDto);

        LocalDate startDate = borrowingBookTransactionDto.getPlannedStartDate();
        if(startDate == null)
            throw new BadRequestException("Booking object without start date!");

        LocalDate endDate = borrowingBookTransactionDto.getPlannedEndDate();
        if(endDate == null)
            throw new BadRequestException("Booking object without end date!");

        Optional<BorrowingBookTransaction> existingBooking =
                borrowingBookTransactionRepository.findBookingByDates(
                        borrowingBookTransactionDto.getPlannedStartDate(),
                        borrowingBookTransactionDto.getPlannedEndDate()
                );

        if(existingBooking.isPresent())
            throw new BadRequestException("Booking between " + startDate + " and " + endDate + " already exists!");

        BookDto bookDto = borrowingBookTransactionDto.getBookDto();
        if(bookDto == null)
            throw new BadRequestException("Request for adding new booking without information about book received!");

        MemberDto memberDto = borrowingBookTransactionDto.getMemberDto();
        if(memberDto == null)
            throw new BadRequestException("Request for adding new booking without information about member received!");

        BorrowingBookTransaction newBorrowingBookTransaction =
                BorrowingBookTransaction.builder()
                        .plannedStartDate(startDate)
                        .plannedEndDate(endDate)
                        .book(bookMapper.bookDtoToBookEntity(bookDto))
                        .member(memberMapper.memberDtoToMemberEntity(memberDto))
                        .bookingDate(LocalDateTime.now())
                        .build();

        BorrowingBookTransactionDto addedBorrowingBookTransaction =
                borrowingBookTransactionMapper.transactionEntityToTransactionDto(
                        borrowingBookTransactionRepository.save(newBorrowingBookTransaction),
                        getGenreDtosForTransaction(newBorrowingBookTransaction.getId()));

        log.debug("Returning added borrowing book booking \n{}", addedBorrowingBookTransaction);
        return addedBorrowingBookTransaction;
    }

    @Override
    public BorrowingBookTransactionDto borrowObjectWithExistingBooking(Long transactionId, Long userId) {

        log.debug("Request for borrowing book by existing transaction {} received from {}", transactionId, userId);

        BorrowingBookTransaction borrowingBookTransaction =
                borrowingBookTransactionRepository
                        .findById(transactionId)
                        .orElseThrow(() -> new NotFoundException(
                                "Borrowing transaction with id = " + transactionId + " doesn't exist!"));

        Member member = borrowingBookTransaction.getMember();
        if(member == null)
            throw new RuntimeException("There is no information about booked member!");

        if(member.getId().equals(userId))
            throw new BadRequestException("User's id and booked member's ids are not equal!");

        LocalDate startDate = borrowingBookTransaction.getPlannedStartDate();
        if(startDate == null)
            throw new RuntimeException("There is no information about booking start date!");

        if(startDate.isAfter(LocalDate.now()))
            throw new BadRequestException("You've come too early! Your booking starts on " + startDate);
        else if(startDate.isBefore(LocalDate.now()))
            borrowingBookTransaction.setPlannedStartDate(LocalDate.now());

        BorrowingBookTransactionDto borrowingBookTransactionDto =
                borrowingBookTransactionMapper.transactionEntityToTransactionDto(
                        borrowingBookTransactionRepository.save(borrowingBookTransaction),
                        getGenreDtosForTransaction(transactionId)
                );

        log.debug("Returning added borrowing book information \n{}", borrowingBookTransactionDto);
        return borrowingBookTransactionDto;
    }

    @Override
    public BorrowingBookTransactionDto returnObject(Long memberId, Long transactionId) {

        log.debug("Request for bringing back book with borrowing transaction {} received from {}", transactionId, memberId);

        memberRepository
                .findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member with id = " + memberId + " doesn't exist!"));

        BorrowingBookTransaction transaction = borrowingBookTransactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction with id = " + memberId + " doesn't exist!"));

        Member memberWhoMadeTransaction = transaction.getMember();
        if(memberWhoMadeTransaction == null)
            throw new RuntimeException("There is no information about member who borrowed the book!");

        if(!memberId.equals(memberWhoMadeTransaction.getId()))
            throw new BadRequestException("Member who took and member who brought back the book are different!");

        transaction.setActualReturnDate(LocalDateTime.now());
        BorrowingBookTransactionDto borrowingBookTransactionDto =
                borrowingBookTransactionMapper.transactionEntityToTransactionDto(
                        borrowingBookTransactionRepository.save(transaction), getGenreDtosForTransaction(transactionId)
                );

        log.debug("Returning returning book information \n{}", borrowingBookTransactionDto);
        return borrowingBookTransactionDto;
    }

    private List<GenreDto> getGenreDtosForTransaction(Long transId) {
        return genreMapper.genreEntityToGenreDtoList(genreRepository.findByTransactionId(transId));
    }
}