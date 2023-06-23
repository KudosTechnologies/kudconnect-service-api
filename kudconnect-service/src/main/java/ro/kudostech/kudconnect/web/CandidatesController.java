package ro.kudostech.kudconnect.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import ro.kudostech.kudconnect.api.CandidatesApi;
import ro.kudostech.kudconnect.api.model.CandidateDto;
import ro.kudostech.kudconnect.service.CandidatesService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CandidatesController implements CandidatesApi {
  private final CandidatesService candidatesService;

  @Override
  public ResponseEntity<CandidateDto> createCandidate(CandidateDto candidateDto) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteCandidate(Long candidateId) {
    return null;
  }

  @Override
  public ResponseEntity<CandidateDto> getCandidateById(@NonNull UUID candidateId) {
    CandidateDto candidateDto = candidatesService.getCandidateById(candidateId.toString());
    return ResponseEntity.ok(candidateDto);
  }

  @Override
  public ResponseEntity<CandidateDto> listCandidates() {
    return null;
  }

  @Override
  public ResponseEntity<CandidateDto> updateCandidate(Long candidateId, CandidateDto candidateDto) {
    return null;
  }
}
