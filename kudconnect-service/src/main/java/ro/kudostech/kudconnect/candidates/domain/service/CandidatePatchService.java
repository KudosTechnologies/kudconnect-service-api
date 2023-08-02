package ro.kudostech.kudconnect.candidates.domain.service;

import static java.util.Map.entry;
import static ro.kudostech.kudconnect.api.server.model.PatchOperationCandidate.PathEnum.FIRSTNAME;

import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.server.model.PatchOperation;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidate;
import ro.kudostech.kudconnect.candidates.adapters.output.persistence.model.CandidateDbo;
import ro.kudostech.kudconnect.common.exception.ConstraintViolationHelper;
import ro.kudostech.kudconnect.common.exception.IllegalPatchArgumentException;

@Slf4j
@Service
public class CandidatePatchService {

  private final Map<
          PatchOperation,
          Map<PatchOperationCandidate.PathEnum, BiConsumer<CandidateDbo, Object>>>
      patchMap =
          Map.of(
              PatchOperation.REPLACE,
              Map.ofEntries(
                  entry(FIRSTNAME, (CandidateDbo c, Object o) -> c.setFirstName(o.toString()))));

  public Set<ConstraintViolation<?>> patch(CandidateDbo candidateDbo, List<PatchOperationCandidate> patchOperations) {
    return patchOperations.stream()
        .map(patchOperationCandidate -> doPatch(candidateDbo, patchOperationCandidate))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }

  private Optional<ConstraintViolation<?>> doPatch(
          CandidateDbo candidateDbo, PatchOperationCandidate patchOperation) {

    final BiConsumer<CandidateDbo, Object> action =
        patchMap.get(patchOperation.getOp()).get(patchOperation.getPath());
    if (action == null) {
      String message =
          String.format(
              "%sing the %s is not possible. Aborting patching %s.",
              patchOperation.getOp().getValue(), patchOperation.getPath().getValue(), candidateDbo);
      log.info(message);
      return Optional.of(
          ConstraintViolationHelper.buildConstraintViolation(
              message, CandidateDbo.class, patchOperation.getPath().getValue()));
    }
    try {
      action.accept(candidateDbo, patchOperation.getValue());
    } catch (IllegalPatchArgumentException e) {
      return Optional.of(
          ConstraintViolationHelper.buildConstraintViolation(
              "must be a valid " + e.getTarget(),
              e.getTarget(),
              patchOperation.getPath().getValue()));
    }
    log.info(String.format("%s is done", patchOperation.getPath().getValue()));
    return Optional.empty();
  }
}
