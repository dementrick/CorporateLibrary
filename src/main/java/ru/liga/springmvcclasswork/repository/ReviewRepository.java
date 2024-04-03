package ru.liga.springmvcclasswork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.springmvcclasswork.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByBookIdAndUserId(Long bookId, Long userId);
}
