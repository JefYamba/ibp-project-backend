package com.jefy.ibp.repositories;

import com.jefy.ibp.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
