package com.library.library.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.library.author.AuthorDto;
import com.library.library.genre.GenreDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BookDto {
    private Long id;
    @NotNull
    @Size(min = 1)
    private String title;
    @NotNull
    private String isbn;
    @NotNull
    private AuthorDto authorDto;
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    @NotNull
    List<GenreDto> genreDtoList;
}