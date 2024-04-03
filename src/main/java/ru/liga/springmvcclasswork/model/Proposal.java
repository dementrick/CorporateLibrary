package ru.liga.springmvcclasswork.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proposal")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String author;
    private String comment;
    private ProposalStatus status;
}
