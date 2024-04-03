package ru.liga.springmvcclasswork.adminService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.springmvcclasswork.exception.ResourceNotFoundException;
import ru.liga.springmvcclasswork.model.Proposal;
import ru.liga.springmvcclasswork.model.ProposalStatus;
import ru.liga.springmvcclasswork.repository.ProposalRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProposalService {

    private final ProposalRepository proposalRepository;
    private final EntityManager em;

    // Метод для отправки предложения о добавлении книги на доработку
    @Transactional
    public void sendProposalForRevision(Long proposalId, String comment) {
        try {
            em.getTransaction().begin();
            Proposal proposal = proposalRepository.findById(proposalId)
                    .orElseThrow(() -> new ResourceNotFoundException("Proposal", "id", proposalId));

            em.lock(proposal, LockModeType.OPTIMISTIC);
            proposal.setComment(comment);
            proposal.setStatus(ProposalStatus.REVISION);
            proposalRepository.save(proposal);
            em.getTransaction().commit();
            em.close();
        } catch (OptimisticLockException e) { log.warn("Данное предложение уже изменено" + e.getMessage()); }
    }

    // Метод для подтверждения предложения о добавлении книги администратором
    @Transactional
    public void confirmProposal(Long proposalId) {
        try {
            em.getTransaction().begin();
            Proposal proposal = proposalRepository.findById(proposalId)
                    .orElseThrow(() -> new ResourceNotFoundException("Proposal", "id", proposalId));

            em.lock(proposal, LockModeType.OPTIMISTIC);
            proposal.setStatus(ProposalStatus.APPROVED);
            proposalRepository.save(proposal);
            em.getTransaction().commit();
            em.close();
        } catch (OptimisticLockException e) { log.warn("Данное предложение уже принято" + e.getMessage()); }
    }
}
