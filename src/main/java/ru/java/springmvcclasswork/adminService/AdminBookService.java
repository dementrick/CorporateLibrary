package ru.java.springmvcclasswork.adminService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.java.springmvcclasswork.model.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final EntityManager entityManager;

    // Метод для поиска книг с возможностью фильтрации и сортировки
    public Page<Book> searchBooks(String titleQuery, Pageable pageable) {
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
}