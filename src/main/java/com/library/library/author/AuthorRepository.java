package com.library.library.author;

import com.library.library.author.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>, CrudRepository<Author, Long> {
    @Query(value = "select count(*) from authors", nativeQuery = true)
    int findAuthorsAmount();
}