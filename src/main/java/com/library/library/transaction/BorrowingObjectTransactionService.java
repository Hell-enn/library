package com.library.library.transaction;

import com.library.library.transaction.BorrowingBookTransactionDto;

public interface BorrowingObjectTransactionService {
    BorrowingBookTransactionDto borrowObject(BorrowingBookTransactionDto borrowingBookTransactionDto);
    BorrowingBookTransactionDto borrowObjectWithExistingBooking(Long transactionId, Long userId);
    BorrowingBookTransactionDto returnObject(Long memberId, Long transactionId);
}