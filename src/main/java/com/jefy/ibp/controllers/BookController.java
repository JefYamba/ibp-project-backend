package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.BookDTO;
import com.jefy.ibp.dtos.BookRequestDTO;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.jefy.ibp.dtos.Constants.BOOKS_URL;

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
    public ResponseEntity<Page<BookDTO>> getAllBooks(
            @RequestParam(defaultValue = "") String searchKey,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.getAll(page,size, searchKey));
    }
    @GetMapping("/latest")
    public ResponseEntity<Page<BookDTO>> getAllLatestBooks(
            @RequestParam(value = "q", defaultValue = "") String searchKey,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(bookService.getAll(page,size, searchKey));
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookDTO> get(@PathVariable("book_id") Long bookId) {
        return ResponseEntity.ok(bookService.getById(bookId));
    }

    @PostMapping
    public ResponseEntity<BookDTO> register(@RequestBody BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(bookService.create(bookRequestDTO));
    }


    @PutMapping
    public ResponseEntity<BookDTO> update(@RequestBody BookRequestDTO bookRequestDTO) {
        try {
            return ResponseEntity.ok(bookService.update(bookRequestDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("book_id") Long bookId) {
        Map<String,String> response = new HashMap<>();
        try {
            bookService.delete(bookId);
            response.put("response", "book deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/{book_id}")
    public ResponseEntity<Map<String,String>> setImageCover(@PathVariable("book_id") Long bookId, @RequestPart MultipartFile image) {
        Map<String,String> response = new HashMap<>();
        try {
            bookService.setImage(bookId, image);
            response.put("response", "image saved successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
