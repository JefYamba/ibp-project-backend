package com.jefy.ibp.services;

import com.jefy.ibp.dtos.BookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface BookService {
    List<BookDTO> getAll();
    BookDTO getById(Long id);
    BookDTO register(BookDTO appUserDto);
    BookDTO update(BookDTO appUserDto);
    void delete(Long id);
    void setImage(Long userId, MultipartFile image);
}
