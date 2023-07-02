package ro.kudostech.kudconnect.usermanagement.internal;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.usermanagement.CandidateCdc;
import ro.kudostech.kudconnect.usermanagement.CandidateService;
import ro.kudostech.kudconnect.usermanagement.internal.domain.Candidate;
import ro.kudostech.kudconnect.usermanagement.internal.repository.CandidateRepository;

@Service
@RequiredArgsConstructor
public class CandidatesServiceImpl implements CandidateService {

  private final CandidateRepository candidateRepository;
  private final CandidateMapper candidateMapper;
  private final ApplicationEventPublisher events;


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
    CandidateCdc candidateCdc = CandidateCdc.builder()
            .id(candidate.getId())
            .email(candidate.getEmail())
            .build();
    events.publishEvent(candidateCdc);
    return candidateMapper.toCandidateDto(candidate);
  }

  @Transactional
  public void deleteCandidate(String userId) {
    candidateRepository.deleteById(userId);
  }

}
