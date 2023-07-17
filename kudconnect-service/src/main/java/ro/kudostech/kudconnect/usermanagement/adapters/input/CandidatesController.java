package ro.kudostech.kudconnect.usermanagement.adapters.input;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.kudostech.kudconnect.api.server.CandidatesApi;
import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidate;
import ro.kudostech.kudconnect.api.server.model.ResourceUUID;
import ro.kudostech.kudconnect.usermanagement.ports.input.CandidateService;


import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CandidatesController implements CandidatesApi {
  private final CandidateService candidateService;

  @Override
  @PreAuthorize("hasRole('admin')")
  public ResponseEntity<ResourceUUID> createCandidate(Candidate candidate) {
    Candidate savedCandidate = candidateService.createCandidate(candidate);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedCandidate.getId())
            .toUri();
    return ResponseEntity.created(location).body(new ResourceUUID().id(savedCandidate.getId()));
  }

  @Override
  public ResponseEntity<Void> deleteCandidate(UUID candidateId) {
    candidateService.deleteCandidate(candidateId.toString());
    return ResponseEntity.noContent().build();
  }

  @Override
  @PreAuthorize("hasRole('user')")
  public ResponseEntity<Candidate> getCandidateById(@NonNull UUID candidateId) {
    Candidate candidate = candidateService.getCandidateById(candidateId.toString());
    return ResponseEntity.ok(candidate);
  }

  @Override
  public ResponseEntity<List<Candidate>> listCandidates() {
    return ResponseEntity.ok(candidateService.getAllCandidates());
  }

  @Override
  public ResponseEntity<Void> patchCandidate(
      UUID candidateId, List<PatchOperationCandidate> patchOperationCandidate) {
    candidateService.updateCandidate(candidateId.toString(), patchOperationCandidate);
    return ResponseEntity.noContent().build();
  }
}
