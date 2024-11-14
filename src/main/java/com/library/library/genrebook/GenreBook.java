package com.library.library.genrebook;

import com.library.library.book.Book;
import com.library.library.genre.Genre;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genres_books", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_book_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}