package ru.java.springmvcclasswork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.springmvcclasswork.model.Book;
import ru.java.springmvcclasswork.model.Proposal;
import ru.java.springmvcclasswork.model.ProposalStatus;
import ru.java.springmvcclasswork.model.Review;
import ru.java.springmvcclasswork.userService.BookProposalService;
import ru.java.springmvcclasswork.userService.BookSearchService;
import ru.java.springmvcclasswork.userService.ReviewService;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class UserLibraryController {

    private final BookSearchService bookSearchService;
    private final ReviewService reviewService;
    private final BookProposalService proposalService;

    @GetMapping("/search/title")
    public ResponseEntity<Page<Book>> searchBooksByTitle(
            @RequestParam("title") String title,
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<Book> books = bookSearchService.searchBooksByTitle(title, pageable);
            return ResponseEntity.ok(books);
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @GetMapping("/search/isbn")
    public ResponseEntity<Page<Book>> searchBooksByIsbn(
            @RequestParam("isbn") String isbn,
            @PageableDefault(sort = "isbn", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<Book> books = bookSearchService.searchBooksByIsbn(isbn, pageable);
            return ResponseEntity.ok(books);
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }

    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> getBookDetails(@PathVariable Long bookId) {
        try {
            Book book = bookSearchService.getBookDetails(bookId);
            if (book != null) {
                return ResponseEntity.ok(book);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @GetMapping("/book/{bookId}/comments")
    public ResponseEntity<Page<Review>> getBookComments(
            @PathVariable Long bookId,
            @RequestParam Integer rating,
            @PageableDefault Pageable pageable) {
        try {
            Page<Review> comments = bookSearchService.getBookComments(bookId, rating, pageable);
            return ResponseEntity.ok(comments);
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @PostMapping("/review/leave")
    public ResponseEntity<Review> leaveReview(
            @RequestParam("bookId") Long bookId,
            @RequestParam("userId") Long userId,
            @RequestParam("rating") Integer rating,
            @RequestParam(value = "comment", required = false) String comment) {
        try {
            Review review = reviewService.leaveReview(bookId, userId, rating, comment);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestParam(value = "comment") String updatedComment,
            @RequestParam(value = "rating", required = false) Integer updatedRating) {
        try {
            Review updatedReview = reviewService.updateReview(reviewId, updatedComment, updatedRating);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/review/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/proposals/{userId}")
    public ResponseEntity<Proposal> proposeNewBook(@PathVariable Long userId, @RequestParam String title, String author) {
        try {
            Proposal proposal = proposalService.proposeNewBook(userId, title, author);
            return ResponseEntity.ok().body(proposal);
        } catch (Exception e) { return ResponseEntity.badRequest().build(); }
    }

    @PutMapping("/proposal/{proposalId}")
    public ResponseEntity<Proposal> updateProposal(@PathVariable Long proposalId, @RequestParam ProposalStatus proposalStatus) {
        try {
            Proposal proposals = proposalService.updateProposal(proposalId, proposalStatus);
            return ResponseEntity.ok().body(proposals);
        } catch (Exception e) { return ResponseEntity.badRequest().build(); }
    }

    @GetMapping("/proposals/{proposalId}")
    public ResponseEntity<Proposal> getProposalById(@PathVariable Long proposalId) {
        try {
            Proposal proposal = proposalService.getProposalById(proposalId);
            return ResponseEntity.ok().body(proposal);
        } catch (Exception e) { return ResponseEntity.badRequest().build(); }
    }

    @DeleteMapping("/proposals/{proposalId}")
    public ResponseEntity<?> deleteProposal(@PathVariable Long proposalId) {
        try {
            proposalService.deleteProposal(proposalId);
            return ResponseEntity.ok().build();
        } catch (Exception e) { return ResponseEntity.badRequest().build(); }
    }
}
