package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.BookResponse;
import com.jefy.ibp.dtos.BookRequest;
import com.jefy.ibp.entities.Book;
import com.jefy.ibp.repositories.BookRepository;
import com.jefy.ibp.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.jefy.ibp.enums.ClassEntity.BOOK;
import static com.jefy.ibp.services.impl.ImageServiceImpl.deleteImageFileFromDirectory;

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
    public Page<BookResponse> getAll(int page, int size, String searchKey) {

        if (searchKey == null || searchKey.isBlank()) {
            return bookRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"title")))
                    .map(BookResponse::fromEntity);
        } else {
            return bookRepository.
                findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrPublisherContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrGenreContainingIgnoreCaseOrSummaryContainingIgnoreCase(
                    searchKey, searchKey,searchKey,searchKey,searchKey,searchKey, PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"title"))
                )
                .map(BookResponse::fromEntity);
        }
    }

    public Page<BookResponse> getAllLatest(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id")))
                .map(BookResponse::fromEntity);

    }

    @Override
    public BookResponse getById(Long id) {
        return bookRepository.findById(id).map(BookResponse::fromEntity).orElseThrow(
                () -> new EntityNotFoundException("book does not exist")
        );
    }

    @Override
    public BookResponse create(BookRequest bookRequest) {
        if (bookRequest == null)
            throw new IllegalArgumentException("book cannot be null");


        return BookResponse.fromEntity(bookRepository.save(
                BookRequest.toEntity(bookRequest)
        ));
    }

    @Override
    public BookResponse update(BookRequest bookRequest){
        if (bookRequest == null || bookRequest.getId() == null)
            throw new IllegalArgumentException("Book or id cannot be null");

        Book book = bookRepository.findById(bookRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book does not exist"));

        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublisher(bookRequest.getPublisher());
        book.setPublicationDate(bookRequest.getPublicationDate());
        book.setIsbn(bookRequest.getIsbn());
        book.setGenre(bookRequest.getGenre());
        book.setSummary(bookRequest.getSummary());

        return BookResponse.fromEntity(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book does not exist")
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
                () -> new EntityNotFoundException("user does not exist")
        );

        if (!(book.getImage() == null || book.getImage().isBlank())){
            deleteImageFileFromDirectory(BOOK, book.getImage());
        }

        book.setImage(ImageServiceImpl.saveImageInDirectory(BOOK, bookId, image));
        bookRepository.save(book);
    }
}
