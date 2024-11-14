package com.library.library.transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BorrowingBookTransactionController {

    private final BorrowingObjectTransactionService borrowingBookTransactionServiceImpl;

    @PostMapping
    public ResponseEntity<Object> postBorrowing(
            @Valid @RequestBody BorrowingBookTransactionDto borrowingBookTransactionDto) {
        log.debug("Request for adding a new booking for borrowing received!\n{}", borrowingBookTransactionDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(borrowingBookTransactionServiceImpl.borrowObject(borrowingBookTransactionDto));
    }

    @PatchMapping("/{transId}/take/{userId}")
    public ResponseEntity<Object> patchBorrowingTake(@PathVariable Long transId, @PathVariable Long userId) {
        log.debug("Request for taking a booked book with id = {} by member with id = {} received!", transId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(borrowingBookTransactionServiceImpl.borrowObjectWithExistingBooking(transId, userId));
    }

    @PatchMapping("/{transId}/return/{userId}")
    public ResponseEntity<Object> patchBorrowingReturn(@PathVariable Long transId, @PathVariable Long userId) {
        log.debug("Request for returning a book with id = {} by member with id = {} received!", transId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(borrowingBookTransactionServiceImpl.returnObject(userId, transId));
    }
}