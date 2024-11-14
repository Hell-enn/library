package com.library.library.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookController {

    private final BookService bookServiceImpl;

    @PostMapping
    public ResponseEntity<Object> postBook(@Valid @RequestBody BookDto bookDto) {
        log.debug("Request for adding new book receiver!\n{}", bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookServiceImpl.addBook(bookDto));
    }
}