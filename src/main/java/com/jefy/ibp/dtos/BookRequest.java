package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

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
public class BookRequest {
    private Long id;
    @NotBlank(message = "Title must be filled in")
    private String title;
    @NotBlank(message = "Author must be filled in")
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private String isbn;
    @NotBlank(message = "Genre must be filled in")
    private String genre;
    private String summary;

    public static Book toEntity(BookRequest bookRequest) {
        if (bookRequest == null || bookRequest.getId() == null) {
            return null;
        }
        return Book.builder()
                .id(bookRequest.getId())
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .publicationDate(bookRequest.getPublicationDate())
                .isbn(bookRequest.getIsbn())
                .genre(bookRequest.getGenre())
                .summary(bookRequest.getSummary())
                .build();
    }
}
