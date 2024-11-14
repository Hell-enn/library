package com.library.library.transaction;

import com.library.library.member.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BorrowingBookTransactionRepository
        extends PagingAndSortingRepository<BorrowingBookTransaction, Long>, CrudRepository<BorrowingBookTransaction, Long> {

    @Query(value = "declare @start DATE = ?1;" +
                   "declare @end DATE = ?2;" +
                   "select top 1 * " +
                   "from borrowing_book_transactions t " +
                   "where t.planned_start_date between @start and @end " +
                   "   or t.planned_end_date between @start and @end  " +
                   "   or t.actual_return_date between @start and @end  " +
                   "   or t.planned_start_date <= @start and t.planned_end_date >= @end  ", nativeQuery = true)
    Optional<BorrowingBookTransaction> findBookingByDates(LocalDate plannedStartDate, LocalDate plannedEndDate);

    @Query(value = "select top 1 * " +
                   "from borrowing_book_transactions t " +
                   "where t.borrowing_book_transaction_id = ?1 and t.member_id = ?2", nativeQuery = true)
    Optional<BorrowingBookTransaction> findBookingByTransactionAndUser(LocalDate plannedStartDate, LocalDate plannedEndDate);

    @Query(value = "select m.* " +
                   "from borrowing_book_transactions t " +
                   "join members m on m.member_id = t.member_id " +
                   "where t.planned_end_date < ?1 and t.actual_return_date is null", nativeQuery = true)
    List<Member> findDebtorsTransactions(LocalDate today);
}