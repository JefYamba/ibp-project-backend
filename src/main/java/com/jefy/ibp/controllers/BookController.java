package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.BookRequest;
import com.jefy.ibp.dtos.BookResponse;
import com.jefy.ibp.dtos.ConfirmationResponse;
import com.jefy.ibp.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.jefy.ibp.dtos.Constants.BOOKS_URL;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(BOOKS_URL)
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {
    private final BookService bookService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all the books",
            description = "Fetch a page of books",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @RequestParam(defaultValue = "") String searchKey,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(bookService.getAll(page,size, searchKey));
    }

    @GetMapping(path = "/latest", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all the books ordered by the latest",
            description = "Fetch a page of books ordered by the latest",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<Page<BookResponse>> getAllLatestBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(bookService.getAllLatest(page,size));
    }

    @GetMapping(path = "/{book_id}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get book by Id",
            description = "fetch a book using the id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    public ResponseEntity<BookResponse> get(@PathVariable("book_id") Long bookId) {
        return ResponseEntity.status(OK).body(bookService.getById(bookId));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Add a book",
            description = "register a new book  [For admin only]",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Not acceptable/ Invalid object", responseCode = "406"),
            }
    )
    public ResponseEntity<BookResponse> register(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity.status(OK).body(bookService.create(bookRequest));
    }


    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update a book  [For admin only]",
            description = "modifies an existing book",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Not acceptable/ Invalid object", responseCode = "406"),
            }
    )
    public ResponseEntity<BookResponse> update(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity.status(OK).body(bookService.update(bookRequest));
    }

    @DeleteMapping(path = "/{book_id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a book [For admin only]",
            description = "Delete an existing book",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            }
    )
    public ResponseEntity<ConfirmationResponse> delete(@PathVariable("book_id") Long bookId) throws IOException {
        bookService.delete(bookId);
        return ResponseEntity.status(OK).body(new ConfirmationResponse("Book deleted successfully"));
    }

    @PostMapping(path = "/{book_id}", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Register book image cover  [For admin only]",
            description = "set an image cover for an existing book",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            }
    )
    public ResponseEntity<ConfirmationResponse> setImageCover(@PathVariable("book_id") Long bookId, @RequestPart MultipartFile image) throws IOException {
        bookService.setImage(bookId, image);
        return ResponseEntity.status(OK).body(new ConfirmationResponse("Book image profile saved successfully"));
    }
}
