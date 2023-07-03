package ro.kudostech.kudconnect.usermanagement.domain.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.common.event.CandidateCdc;
import ro.kudostech.kudconnect.usermanagement.domain.exception.CandidateAlreadyExistsException;
import ro.kudostech.kudconnect.usermanagement.domain.mapper.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.infrastructure.adapters.output.persistence.CandidateRepository;
import ro.kudostech.kudconnect.usermanagement.ports.input.CandidateService;
import ro.kudostech.kudconnect.usermanagement.domain.model.Candidate;
import ro.kudostech.kudconnect.usermanagement.ports.output.eventpublisher.CandidateEventPublisher;

@Service
@RequiredArgsConstructor
public class CandidatesServiceImpl implements CandidateService {

  private final CandidateRepository candidateRepository;
  private final CandidateMapper candidateMapper;
  private final CandidateEventPublisher eventPublisher;

  public CandidateDto getCandidateById(String userId) {
    return candidateRepository
        .findById(userId)
        .map(candidateMapper::toCandidateDto)
        .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
  }

  public List<CandidateDto> getAllCandidates() {
    return candidateRepository.findAll().stream().map(candidateMapper::toCandidateDto).toList();
  }

  @Transactional
  public CandidateDto createCandidate(CandidateDto candidateDto) {
    Candidate candidate = candidateMapper.toCandidate(candidateDto);
    candidateRepository.save(candidate);
    CandidateCdc candidateCdc =
        CandidateCdc.builder().id(candidate.getId()).email(candidate.getEmail()).build();
    eventPublisher.publishCandidateCDCEvent(candidateCdc);
    return candidateMapper.toCandidateDto(candidate);
  }

  @Transactional
  public void deleteCandidate(String userId) {
    candidateRepository.deleteById(userId);
  }
}
