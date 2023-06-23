package ro.kudostech.kudconnect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.model.CandidateDto;
import ro.kudostech.kudconnect.domain.Candidate;

@Service
@RequiredArgsConstructor
public class CandidatesService {
  private static final String STATIC_USER_ID = "c2135746-e634-4283-b352-f6179c1abd02";

  private final CandidateMapper candidateMapper;

  public CandidateDto getCandidateById(String userId) {
    if (userId.equals(STATIC_USER_ID)) {
      return candidateMapper.toCandidateDto(getMockCandidate());
    } else {
      throw new RuntimeException("User with id " + userId + " not found");
    }
  }

  private Candidate getMockCandidate() {
    return Candidate.builder()
        .id(STATIC_USER_ID)
        .firstName("John")
        .lastName("Doe")
        .email("upchh@example.com")
        .phone("0123456789")
        .build();
  }
}
