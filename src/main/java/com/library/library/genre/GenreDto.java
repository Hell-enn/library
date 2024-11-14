package com.library.library.genre;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GenreDto {
    private Long id;
    @NotNull
    @Size(min = 1, max = 150)
    private String title;
    @NotNull
    @Size(min = 1, max = 1000)
    private String description;
}