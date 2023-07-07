package ro.kudostech.kudconnect.usermanagement.infrastructure.adapters.input;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.kudostech.kudconnect.api.server.CandidatesApi;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidateDto;
import ro.kudostech.kudconnect.api.server.model.ResourceUUIDDto;
import ro.kudostech.kudconnect.usermanagement.ports.input.CandidateService;


import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CandidatesController implements CandidatesApi {
  private final CandidateService candidateService;

  @Override
  public ResponseEntity<ResourceUUIDDto> createCandidate(CandidateDto candidateDto) {
    CandidateDto savedCandidate = candidateService.createCandidate(candidateDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedCandidate.getId())
            .toUri();
    return ResponseEntity.created(location).body(new ResourceUUIDDto().id(savedCandidate.getId()));
  }

  @Override
  public ResponseEntity<Void> deleteCandidate(UUID candidateId) {
    candidateService.deleteCandidate(candidateId.toString());
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<CandidateDto> getCandidateById(@NonNull UUID candidateId) {
    CandidateDto candidateDto = candidateService.getCandidateById(candidateId.toString());
    return ResponseEntity.ok(candidateDto);
  }

  @Override
  public ResponseEntity<List<CandidateDto>> listCandidates() {
    return ResponseEntity.ok(candidateService.getAllCandidates());
  }

  @Override
  public ResponseEntity<Void> patchCandidate(
      UUID candidateId, List<PatchOperationCandidateDto> patchOperationCandidateDto) {
    candidateService.updateCandidate(candidateId.toString(), patchOperationCandidateDto);
    return ResponseEntity.noContent().build();
  }
}
