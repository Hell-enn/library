package com.library.library.genre;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genres", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", insertable = false, updatable = false)
    private Long id;
    private String title;
    private String description;
}