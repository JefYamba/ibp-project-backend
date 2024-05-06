package com.jefy.ibp.services;

import com.jefy.ibp.dtos.BookDTO;
import com.jefy.ibp.dtos.BookRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface BookService {
    List<BookDTO> getAll();
    BookDTO getById(Long id);
    BookDTO create(BookRequestDTO bookRequestDTO);
    BookDTO update(BookRequestDTO bookRequestDTO) throws Exception;
    void delete(Long id) throws IOException;
    void setImage(Long bookId, MultipartFile image) throws IOException;
}
