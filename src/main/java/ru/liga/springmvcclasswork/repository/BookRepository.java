package ru.liga.springmvcclasswork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.springmvcclasswork.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
