package ru.java.springmvcclasswork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.springmvcclasswork.model.Book;
import ru.java.springmvcclasswork.adminService.AdminBookService;
import ru.java.springmvcclasswork.adminService.AdminProposalService;
import ru.java.springmvcclasswork.adminService.AdminReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLibraryController {

    private final AdminBookService bookService;
    private final AdminReviewService reviewService;
    private final AdminProposalService proposalService;

    @GetMapping("/search")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam("title") String title,
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<Book> books = bookService.searchBooks(title, pageable);
            return ResponseEntity.ok(books);
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @GetMapping("/isbn")
    public ResponseEntity<Page<Book>> searchBooksByIsbn(
            @RequestParam("isbn") String isbn,
            @PageableDefault(sort = "isbn", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<Book> books = bookService.searchBooksByIsbn(isbn, pageable);
            return ResponseEntity.ok(books);
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok().build();
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @PostMapping("/revision/{proposalId}")
    public ResponseEntity<?> sendProposalForRevision(@PathVariable Long proposalId, @RequestParam String comment) {
        try {
            proposalService.sendProposalForRevision(proposalId, comment);
            return ResponseEntity.ok().build();
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }

    @PostMapping("/approve/{proposalId}")
    public ResponseEntity<?> confirmProposal(@PathVariable Long proposalId) {
        try {
            proposalService.confirmProposal(proposalId);
            return ResponseEntity.ok().build();
        } catch (Exception e) { return ResponseEntity.internalServerError().build(); }
    }
}
