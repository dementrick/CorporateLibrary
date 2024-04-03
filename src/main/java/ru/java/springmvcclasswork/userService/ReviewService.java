package ru.java.springmvcclasswork.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.java.springmvcclasswork.exception.ResourceNotFoundException;
import ru.java.springmvcclasswork.model.Book;
import ru.java.springmvcclasswork.model.Review;
import ru.java.springmvcclasswork.model.User;
import ru.java.springmvcclasswork.repository.BookRepository;
import ru.java.springmvcclasswork.repository.ReviewRepository;
import ru.java.springmvcclasswork.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // Метод для оставления отзыва о книге
    public Review leaveReview(Long bookId, Long userId, Integer rating, String comment) {

        Review existingReview = reviewRepository.findByBookIdAndUserId(bookId, userId);
        if (existingReview != null) {
            existingReview.setRating(rating);
            return reviewRepository.save(existingReview);
        } else {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
            Review review = new Review();
            review.setBook(book);
            review.setUser(user);
            review.setRating(rating);
            if (comment != null) review.setComment(comment);
            return reviewRepository.save(review);
        }
    }

    // Метод для обновления отзыва
    public Review updateReview(Long reviewId, String updateComment, Integer updateRating) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        existingReview.setRating(updateRating);
        existingReview.setComment(updateComment);

        return reviewRepository.save(existingReview);
    }

    // Метод для удаления отзыва
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        reviewRepository.delete(review);
    }
}
