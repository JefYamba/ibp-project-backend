package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.BookDTO;
import com.jefy.ibp.dtos.BookRequestDTO;
import com.jefy.ibp.entities.Book;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.BookRepository;
import com.jefy.ibp.services.BookService;
import com.jefy.ibp.validators.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.jefy.ibp.enums.ClassEntity.BOOK;
import static com.jefy.ibp.services.impl.ImageService.deleteImageFileFromDirectory;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Override
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(BookDTO::fromEntity)
                .toList();
    }

    @Override
    public BookDTO getById(Long id) {
        return bookRepository.findById(id).map(BookDTO::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("book does not exist")
        );
    }

    @Override
    public BookDTO create(BookRequestDTO bookRequestDTO) {
        if (bookRequestDTO == null)
            throw new IllegalArgumentException("bookDTO cannot be null");

        Map<String, String> errors = BookValidator.validateBook(bookRequestDTO);

        if (!errors.isEmpty()){
            throw new EntityNotValidException(errors);
        }

        return BookDTO.fromEntity(bookRepository.save(
                BookRequestDTO.toEntity(bookRequestDTO)
        ));
    }

    @Override
    public BookDTO update(BookRequestDTO bookRequestDTO) throws Exception {
        if (bookRequestDTO == null || bookRequestDTO.getId() == null)
            throw new IllegalArgumentException("BookRequestDTO Or Id cannot be null");

        Map<String, String> errorsUser = BookValidator.validateBook(bookRequestDTO);
        if (!errorsUser.isEmpty()){
            throw new EntityNotValidException(errorsUser);
        }

        Book book = bookRepository.findById(bookRequestDTO.getId())
                .orElseThrow(() -> new RecordNotFoundException("Book does not exist"));

        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setPublisher(bookRequestDTO.getPublisher());
        book.setPublicationDate(bookRequestDTO.getPublicationDate());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setGenre(bookRequestDTO.getGenre());
        book.setSummary(bookRequestDTO.getSummary());

        return BookDTO.fromEntity(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Book does not exist")
        );
        if (!(book.getImage() == null || book.getImage().isBlank())){
            deleteImageFileFromDirectory(BOOK, book.getImage());
        }
        bookRepository.delete(book);
    }

    @Override
    public void setImage(Long bookId, MultipartFile image) throws IOException {
        if (image == null || bookId == null || image.isEmpty() ){
            throw new IllegalArgumentException("Book Id and image cannot be null");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );

        if (!(book.getImage() == null || book.getImage().isBlank())){
            deleteImageFileFromDirectory(BOOK, book.getImage());
        }

        book.setImage(ImageService.saveImageInDirectory(BOOK, bookId, image));
        bookRepository.save(book);
    }
}