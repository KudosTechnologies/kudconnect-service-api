package ro.kudostech.kudconnect.usermanagement;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.apiserver.CandidateDto;
import ro.kudostech.kudconnect.usermanagement.internal.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.internal.domain.Candidate;
import ro.kudostech.kudconnect.usermanagement.internal.repository.CandidateRepository;

@Service
@RequiredArgsConstructor
public class CandidatesService {

  private final CandidateRepository candidateRepository;
  private final CandidateMapper candidateMapper;

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
    return candidateMapper.toCandidateDto(candidate);
  }

  @Transactional
  public void deleteCandidate(String userId) {
    candidateRepository.deleteById(userId);
  }

}
