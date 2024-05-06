package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.Book;
import lombok.*;

import java.time.LocalDate;

import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.utils.ImageUtility.getUrl;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private String isbn;
    private String genre;
    private String summary;

    public static Book toEntity(BookRequestDTO bookRequestDTO) {
        return Book.builder()
                .id(bookRequestDTO.getId())
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .publisher(bookRequestDTO.getPublisher())
                .publicationDate(bookRequestDTO.getPublicationDate())
                .isbn(bookRequestDTO.getIsbn())
                .genre(bookRequestDTO.getGenre())
                .summary(bookRequestDTO.getSummary())
                .build();
    }
}
