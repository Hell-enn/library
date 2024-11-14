package com.library.library.genrebook;

import com.library.library.book.BookMapper;
import com.library.library.genrebook.GenreBookDto;
import com.library.library.genrebook.GenreBook;
import com.library.library.genre.GenreMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GenreBookMapper {

    private final GenreMapper genreMapper;
    private final BookMapper bookMapper;

    public GenreBook genreBookDtoToGenreBookEntity(GenreBookDto genreBookDto) {
        return GenreBook.builder()
                .id(genreBookDto.getId())
                .genre(genreMapper.genreDtoToGenreEntity(genreBookDto.getGenreDto()))
                .book(bookMapper.bookShortDtoToBookEntity(genreBookDto.getBookShortDto()))
                .build();
    }

    public GenreBookDto genreBookEntityToGenreBookDto(GenreBook genreBook) {
        return GenreBookDto.builder()
                .id(genreBook.getId())
                .genreDto(genreMapper.genreEntityToGenreDto(genreBook.getGenre()))
                .bookShortDto(bookMapper.bookEntityToBookShortDto(genreBook.getBook()))
                .build();
    }
}