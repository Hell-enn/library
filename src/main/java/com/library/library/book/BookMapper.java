package com.library.library.book;

import com.library.library.author.AuthorMapper;
import com.library.library.genre.GenreDto;
import com.library.library.genre.GenreMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BookMapper {
    private final AuthorMapper authorMapper;

    public Book bookDtoToBookEntity(@NotNull BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .isbn(bookDto.getIsbn())
                .author(authorMapper.authorDtoToAuthorEntity(bookDto.getAuthorDto()))
                .publicationDate(bookDto.getPublicationDate())
                .build();
    }

    public BookDto bookEntityToBookDto(Book book, List<GenreDto> genreDtoList) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .authorDto(authorMapper.authorEntityToAuthorDto(book.getAuthor()))
                .publicationDate(book.getPublicationDate())
                .genreDtoList(genreDtoList)
                .build();
    }

    public BookShortDto bookEntityToBookShortDto(Book book) {
        return BookShortDto.builder()
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .authorDto(authorMapper.authorEntityToAuthorDto(book.getAuthor()))
                .publicationDate(book.getPublicationDate())
                .build();
    }

    public Book bookShortDtoToBookEntity(@NotNull BookShortDto bookShortDto) {
        return Book.builder()
                .title(bookShortDto.getTitle())
                .isbn(bookShortDto.getIsbn())
                .author(authorMapper.authorDtoToAuthorEntity(bookShortDto.getAuthorDto()))
                .publicationDate(bookShortDto.getPublicationDate())
                .build();
    }
}