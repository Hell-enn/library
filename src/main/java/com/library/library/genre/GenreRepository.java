package com.library.library.genre;

import com.library.library.genre.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>, CrudRepository<Genre, Long> {
    @Query(value = "select genre_id from genres where genre_id in ?1", nativeQuery = true)
    List<Long> findExistingIds(List<Long> ids);

    @Query(value = "select g.* " +
                    "from genres g " +
                    "join genres_books gb on gb.genre_id = g.genre_id " +
                    "join books b on b.book_id = gb.book_id " +
                    "join borrowing_book_transactions t on t.book_id = b.book_id " +
                    "where t.borrowing_book_transaction_id = ?1", nativeQuery = true)
    List<Genre> findByTransactionId(Long transId);
}