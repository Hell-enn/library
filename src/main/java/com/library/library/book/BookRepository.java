package com.library.library.book;

import com.library.library.book.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>, CrudRepository<Book, Long> {
}