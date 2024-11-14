package com.library.library.author;

import com.library.library.author.AuthorDto;
import com.library.library.author.Author;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class AuthorMapper {
    public Author authorDtoToAuthorEntity(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.getId())
                .firstName(authorDto.getFirstName())
                .lastName(authorDto.getLastName())
                .birthDate(authorDto.getBirthDate())
                .build();
    }

    public AuthorDto authorEntityToAuthorDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .build();
    }

    public List<Author> authorDtoToAuthorEntityList(Page<AuthorDto> authorDtoPage) {
        List<Author> authors = new ArrayList<>();
        authorDtoPage
                .stream()
                .forEach(authorDto -> authors.add(authorDtoToAuthorEntity(authorDto)));
        return authors;
    }

    public List<AuthorDto> authorEntityToAuthorDtoList(Page<Author> authorPage) {
        List<AuthorDto> authorDtos = new ArrayList<>();
        authorPage
                .stream()
                .forEach(author -> authorDtos.add(authorEntityToAuthorDto(author)));
        return authorDtos;
    }

    public void updateAuthor(Author author, AuthorDto authorDto) {
        String firstName = authorDto.getFirstName();
        String lastName = authorDto.getLastName();
        LocalDate birthDate = authorDto.getBirthDate();

        if(firstName != null && !firstName.isEmpty())
            author.setFirstName(firstName);

        if(firstName != null && !firstName.isEmpty())
            author.setLastName(lastName);

        if(firstName != null)
            author.setBirthDate(birthDate);
    }
}