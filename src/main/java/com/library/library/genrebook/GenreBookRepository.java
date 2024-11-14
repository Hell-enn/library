package com.library.library.genrebook;

import com.library.library.genrebook.GenreBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenreBookRepository extends PagingAndSortingRepository<GenreBook, Long>, CrudRepository<GenreBook, Long> {
}