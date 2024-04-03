package ru.java.springmvcclasswork.userService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.java.springmvcclasswork.model.Book;
import ru.java.springmvcclasswork.model.Review;
import ru.java.springmvcclasswork.repository.ReviewRepository;
import ru.java.springmvcclasswork.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookSearchService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;

    // Метод для поиска книг по названию с возможностью фильтрации и сортировки
    public Page<Book> searchBooksByTitle(String titleQuery, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        // Фильтрация по названию книги
        Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + titleQuery.toLowerCase() + "%");
        query.where(titlePredicate);
        query.orderBy(criteriaBuilder.asc(root.get("title")));

        TypedQuery<Book> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Book> result = typedQuery.getResultList();

        return new PageImpl<>(result, pageable, result.size());
    }

    // Метод для поиска книг по ISBN с возможностью фильтрации и сортировки
    public Page<Book> searchBooksByIsbn(String isbnQuery, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        // Фильтрация по ISBN книги
        Predicate isbnPredicate = criteriaBuilder.equal(root.get("isbn"), isbnQuery);
        query.where(isbnPredicate);
        query.orderBy(criteriaBuilder.asc(root.get("isbn")));

        TypedQuery<Book> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Book> result = typedQuery.getResultList();

        return new PageImpl<>(result, pageable, result.size());
    }

    // Метод для получения подробной информации о книге
    public Book getBookDetails(Long bookId) {
        try {
            return bookRepository.findById(bookId).orElse(null);
        } catch (Exception e) {
            log.warn("Книга не найдена " + e.getMessage());
            return null;
        }
    }

    // Метод для получения списка комментариев пользователей по книге
    public Page<Review> getBookComments(Long bookId, Integer rating, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);

        // Фильтрация рейтингу
        Predicate bookPredicate = criteriaBuilder.equal(root.get("book").get("id"), bookId);
        Predicate ratingPredicate = criteriaBuilder.equal(root.get("rating"), rating);
        query.where(criteriaBuilder.and(bookPredicate, ratingPredicate));

        // Сортировка по рейтингу
        query.orderBy(criteriaBuilder.asc(root.get("rating")));

        // Создание запроса
        TypedQuery<Review> typedQuery = entityManager.createQuery(query);

        // Установка параметров страницы
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Получение списка результатов
        List<Review> result = typedQuery.getResultList();
        return new PageImpl<>(result, pageable, result.size());
    }
}
