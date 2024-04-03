package ru.java.springmvcclasswork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.springmvcclasswork.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
