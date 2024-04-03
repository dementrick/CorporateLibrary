package ru.java.springmvcclasswork.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "image_url")
    private String imageUrl;

    private String isbn;

    @Column(name = "isbn13")
    private Double isbn13;

    private String name;

    @Column(name = "original_publication_year")
    private Integer originalPublicationYear;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(name = "small_image_url")
    private String smallImageUrl;

    private String title;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "ratings_count")
    private Integer ratingsCount;

    @Column(name = "average_rating")
    private Double averageRating;
}




