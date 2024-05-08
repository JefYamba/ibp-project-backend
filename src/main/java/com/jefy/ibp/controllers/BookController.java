package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.BookRequestDTO;
import com.jefy.ibp.dtos.ResponseDTO;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.jefy.ibp.dtos.Constants.BOOKS_URL;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(BOOKS_URL)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(
            summary = "Get all the books",
            description = "Fetch a page of books",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<ResponseDTO> getAllBooks(
            @RequestParam(defaultValue = "") String searchKey,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResponseDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(OK)
                .statusCode(OK.value())
                .message("books fetched successfully")
                .data(of("books", bookService.getAll(page,size, searchKey)))
                .build()
        );
    }

    @GetMapping("/latest")
    @Operation(
            summary = "Get all the books ordered by the latest",
            description = "Fetch a page of books ordered by the latest",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<ResponseDTO> getAllLatestBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResponseDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(OK)
                .statusCode(OK.value())
                .message("books fetched successfully")
                .data(of("books", bookService.getAllLatest(page,size)))
                .build()
        );
    }

    @GetMapping("/{book_id}")
    @Operation(
            summary = "Get book by Id",
            description = "fetch a book using the id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    public ResponseEntity<ResponseDTO> get(@PathVariable("book_id") Long bookId) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("book fetched successfully")
                    .data(of("book", bookService.getById(bookId)))
                    .build()

            );
        } catch (RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_FOUND)
                    .statusCode(NOT_FOUND.value())
                    .message("book not found")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @PostMapping
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
    public ResponseEntity<ResponseDTO> register(@RequestBody BookRequestDTO bookRequestDTO) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .message("Book added successfully")
                    .data(of("book", bookService.create(bookRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not create an new book")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (EntityNotValidException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_ACCEPTABLE)
                    .statusCode(NOT_ACCEPTABLE.value())
                    .message("Could not create an new book, all the required field must be correctly filled")
                    .errors(of("errors", e.getErrors()))
                    .build()
            );
        }

    }


    @PutMapping
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
    public ResponseEntity<ResponseDTO> update(@RequestBody BookRequestDTO bookRequestDTO) {
        try {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("Book updated successfully")
                    .data(of("book", bookService.update(bookRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not update book")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (EntityNotValidException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_ACCEPTABLE)
                    .statusCode(NOT_ACCEPTABLE.value())
                    .message("Could not update an new book, all the required field must be correctly filled")
                    .errors(of("errors", e.getErrors()))
                    .build()
            );
        }
    }

    @DeleteMapping("/{book_id}")
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
    public ResponseEntity<ResponseDTO> delete(@PathVariable("book_id") Long bookId) {
        try {
            bookService.delete(bookId);
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("Book deleted successfully")
                    .build()

            );
        }catch (RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Book does not exist")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .message("Could not delete the book, problem occurred on deleting the image")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }

    @PostMapping("/{book_id}")
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
    public ResponseEntity<ResponseDTO> setImageCover(@PathVariable("book_id") Long bookId, @RequestPart MultipartFile image) {
        try {
            bookService.setImage(bookId, image);
            return ResponseEntity.ok(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("Book deleted successfully")
                    .build()

            );
        }catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not save the image")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .message("Could not save the image")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }
}
