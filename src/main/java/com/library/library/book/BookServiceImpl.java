package com.library.library.book;

import com.library.library.genre.GenreDto;
import com.library.library.genre.Genre;
import com.library.library.genrebook.GenreBook;
import com.library.library.exceptions.BadRequestException;
import com.library.library.genre.GenreMapper;
import com.library.library.genrebook.GenreBookRepository;
import com.library.library.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final GenreBookRepository genreBookRepository;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;

    @Override
    public BookDto addBook(BookDto bookDto) {
        log.debug("Request for adding book received \n{}", bookDto);

        List<GenreDto> genreDtoList = bookDto.getGenreDtoList();
        if(genreDtoList == null)
            throw new BadRequestException("Genres are missing!");

        List<Genre> genreList = genreMapper.genreDtoToGenreEntityList(genreDtoList);
        if(genreList == null)
            throw new RuntimeException("Error while mapping list with genres!");

        List<Long> genreIds = new ArrayList<>();
        genreList.forEach(genre -> genreIds.add(genre.getId()));

        List<Long> existingGenres = genreRepository.findExistingIds(genreIds);

        Book newBook = bookMapper.bookDtoToBookEntity(bookDto);
        genreList.forEach(genre -> {
            Long genreId = genre.getId();
            if(!existingGenres.contains(genreId))
                throw new BadRequestException("There is no such genre with id = " + genreId + "!");

            GenreBook genreBook = GenreBook.builder().book(newBook).genre(genre).build();
            genreBookRepository.save(genreBook);
        });

        BookDto addedBook = bookMapper.bookEntityToBookDto(bookRepository.save(newBook), genreDtoList);

        log.debug("Returning added book \n{}", addedBook);
        return addedBook;
    }
}