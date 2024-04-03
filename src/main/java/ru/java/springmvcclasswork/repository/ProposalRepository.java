package ru.java.springmvcclasswork.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.springmvcclasswork.model.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Page<Proposal> findAll(Pageable pageable);

}
