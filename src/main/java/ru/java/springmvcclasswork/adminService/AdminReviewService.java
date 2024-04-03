package ru.java.springmvcclasswork.adminService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.java.springmvcclasswork.exception.ResourceNotFoundException;
import ru.java.springmvcclasswork.model.Review;
import ru.java.springmvcclasswork.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;

    // Метод для удаления отзыва администратором
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        reviewRepository.delete(review);
    }
}