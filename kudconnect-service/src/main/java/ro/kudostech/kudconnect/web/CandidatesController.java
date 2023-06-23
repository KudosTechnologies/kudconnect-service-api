package ro.kudostech.kudconnect.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.kudostech.kudconnect.api.CandidatesApi;
import ro.kudostech.kudconnect.api.model.CandidateDto;
import ro.kudostech.kudconnect.api.model.ResourceUUIDDto;
import ro.kudostech.kudconnect.service.CandidatesService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CandidatesController implements CandidatesApi {
  private final CandidatesService candidatesService;

  @Override
  public ResponseEntity<ResourceUUIDDto> createCandidate(CandidateDto candidateDto) {
    CandidateDto savedCandidate = candidatesService.createCandidate(candidateDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedCandidate.getId())
            .toUri();
    return ResponseEntity.created(location).body(new ResourceUUIDDto().id(savedCandidate.getId()));
  }

  @Override
  public ResponseEntity<Void> deleteCandidate(UUID candidateId) {
    candidatesService.deleteCandidate(candidateId.toString());
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<CandidateDto> getCandidateById(@NonNull UUID candidateId) {
    CandidateDto candidateDto = candidatesService.getCandidateById(candidateId.toString());
    return ResponseEntity.ok(candidateDto);
  }

  @Override
  public ResponseEntity<List<CandidateDto>> listCandidates() {
    return ResponseEntity.ok(candidatesService.getAllCandidates());
  }

  @Override
  public ResponseEntity<CandidateDto> updateCandidate(Long candidateId, CandidateDto candidateDto) {
    return null;
  }
}
