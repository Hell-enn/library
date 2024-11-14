package com.library.library.book;

import com.library.library.author.Author;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String isbn;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
}