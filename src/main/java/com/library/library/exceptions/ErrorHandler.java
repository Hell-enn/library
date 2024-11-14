package com.library.library.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.library.book.BookController;
import com.library.library.transaction.BorrowingBookTransactionController;
import com.library.library.member.MemberController;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice(assignableTypes = {BookController.class,
                                         BorrowingBookTransactionController.class,
                                         MemberController.class})
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleBadRequest(final BadRequestException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Bad request")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleNotFound(final NotFoundException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Object doesn't exist")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleConflict(final ConflictException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Conflict while object check")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleForbidden(final ForbiddenException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Forbidden operation")
                .status(HttpStatus.FORBIDDEN.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object>  handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Incorrect method argument passed")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object>  handleDataIntegrityViolation(DataIntegrityViolationException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Error while sql insert/update")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Operation doesn't exist")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Illegal argument passed")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Incorrect HTTP-request")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Error while sql insert/update because of the constraint")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleJsonProcessingExceptionException(JsonProcessingException e) {
        ApiError apiError = ApiError.builder()
                .errors(List.of(""))
                .message(e.getMessage())
                .reason("Error while JSON serialization/deserialization")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }
}