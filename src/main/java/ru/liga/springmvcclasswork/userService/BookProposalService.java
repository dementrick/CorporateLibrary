package ru.liga.springmvcclasswork.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.springmvcclasswork.exception.ResourceNotFoundException;
import ru.liga.springmvcclasswork.model.Proposal;
import ru.liga.springmvcclasswork.model.ProposalStatus;
import ru.liga.springmvcclasswork.model.User;
import ru.liga.springmvcclasswork.repository.ProposalRepository;
import ru.liga.springmvcclasswork.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class BookProposalService {

    private final ProposalRepository proposalRepository;
    private final UserRepository userRepository;

    public Proposal proposeNewBook(Long userId, String title, String author) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь", "ID", userId));

        Proposal proposal = new Proposal();
        proposal.setUser(user);
        proposal.setTitle(title);
        proposal.setAuthor(author);
        proposal.setStatus(ProposalStatus.PENDING);
        return proposalRepository.save(proposal);
    }

    public Proposal updateProposal(Long proposalId, ProposalStatus newStatus) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Предложение о книге", "ID", proposalId));

        proposal.setStatus(newStatus);
        return proposalRepository.save(proposal);
    }

    public Proposal getProposalById(Long proposalId) {
        return proposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Предложение о книге", "ID", proposalId));
    }

    public void deleteProposal(Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Предложение о книге", "ID", proposalId));

        proposalRepository.delete(proposal);
    }
}
