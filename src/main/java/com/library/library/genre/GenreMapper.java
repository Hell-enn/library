package com.library.library.genre;

import com.library.library.genre.GenreDto;
import com.library.library.genre.Genre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class GenreMapper {

    public Genre genreDtoToGenreEntity(GenreDto genreDto) {
        return Genre.builder()
                .id(genreDto.getId())
                .title(genreDto.getTitle())
                .description(genreDto.getDescription())
                .build();
    }

    public GenreDto genreEntityToGenreDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .title(genre.getTitle())
                .description(genre.getDescription())
                .build();
    }

    public List<Genre> genreDtoToGenreEntityList(List<GenreDto> genreDtoList) {
        List<Genre> genreList = new ArrayList<>();
        genreDtoList.forEach(genreDto -> genreList.add(genreDtoToGenreEntity(genreDto)));
        return genreList;
    }

    public List<GenreDto> genreEntityToGenreDtoList(List<Genre> genreList) {
        List<GenreDto> genreDtoList = new ArrayList<>();
        genreList.forEach(genre -> genreDtoList.add(genreEntityToGenreDto(genre)));
        return genreDtoList;
    }
}