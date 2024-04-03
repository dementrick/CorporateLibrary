package ru.liga.springmvcclasswork.adminService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.springmvcclasswork.exception.ResourceNotFoundException;
import ru.liga.springmvcclasswork.model.Review;
import ru.liga.springmvcclasswork.repository.ReviewRepository;

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