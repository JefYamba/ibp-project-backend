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
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private String isbn;
    private String genre;
    private String summary;
    private String image;

    public static BookDTO fromEntity(Book book) {
        if (book == null || book.getId() == null ) {
            return null;
        }
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .isbn(book.getIsbn())
                .genre(book.getGenre())
                .summary(book.getSummary())
                .image(
                        (book.getImage() == null || book.getImage().isBlank())? "" : getUrl(APP_USER,book.getImage())
                )
                .build();
    }
}
