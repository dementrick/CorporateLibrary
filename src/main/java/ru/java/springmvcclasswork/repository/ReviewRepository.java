package ru.java.springmvcclasswork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.springmvcclasswork.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByBookIdAndUserId(Long bookId, Long userId);
}
