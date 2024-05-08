package com.jefy.ibp.services;

import com.jefy.ibp.dtos.BookDTO;
import com.jefy.ibp.dtos.BookRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface BookService {
    Page<BookDTO> getAll(int page, int size, String searchKey);
    Page<BookDTO> getAllLatest(int page, int size);
    BookDTO getById(Long id);
    BookDTO create(BookRequestDTO bookRequestDTO);
    BookDTO update(BookRequestDTO bookRequestDTO);
    void delete(Long id) throws IOException;
    void setImage(Long bookId, MultipartFile image) throws IOException;
}
