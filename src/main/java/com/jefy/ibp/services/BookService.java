package com.jefy.ibp.services;

import com.jefy.ibp.dtos.BookResponse;
import com.jefy.ibp.dtos.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface BookService {
    Page<BookResponse> getAll(int page, int size, String searchKey);
    Page<BookResponse> getAllLatest(int page, int size);
    BookResponse getById(Long id);
    BookResponse create(BookRequest bookRequest);
    BookResponse update(BookRequest bookRequest);
    void delete(Long id) throws IOException;
    void setImage(Long bookId, MultipartFile image) throws IOException;
}
