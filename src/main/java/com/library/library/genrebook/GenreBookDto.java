package com.library.library.genrebook;

import com.library.library.book.BookShortDto;
import com.library.library.genre.GenreDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GenreBookDto {
    private Long id;
    @NotNull
    private GenreDto genreDto;
    @NotNull
    private BookShortDto bookShortDto;
}