package ro.kudostech.kudconnect.usermanagement.domain.service;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidateDto;
import ro.kudostech.kudconnect.api.server.model.PatchOperationDto;
import ro.kudostech.kudconnect.common.exception.IllegalPatchArgumentException;
import ro.kudostech.kudconnect.common.exception.ConstraintViolationHelper;
import ro.kudostech.kudconnect.usermanagement.domain.model.Candidate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.util.Map.entry;
import static ro.kudostech.kudconnect.api.server.model.PatchOperationCandidateDto.PathEnum.FIRSTNAME;

@Slf4j
@Service
public class CandidatePatchService {

  private final Map<
          PatchOperationDto,
          Map<PatchOperationCandidateDto.PathEnum, BiConsumer<Candidate, Object>>>
      patchMap =
          Map.of(
              PatchOperationDto.REPLACE,
              Map.ofEntries(
                  entry(FIRSTNAME, (Candidate c, Object o) -> c.setFirstName(o.toString()))));

  public Set patch(Candidate candidate, List<PatchOperationCandidateDto> patchOperations) {
    return patchOperations.stream()
        .map(patchOperationCandidateDto -> doPatch(candidate, patchOperationCandidateDto))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }

  private Optional<ConstraintViolation> doPatch(
      Candidate candidate, PatchOperationCandidateDto patchOperation) {

    final BiConsumer<Candidate, Object> action =
        patchMap.get(patchOperation.getOp()).get(patchOperation.getPath());
    if (action == null) {
      String message =
          String.format(
              "%sing the %s is not possible. Aborting patching %s.",
              patchOperation.getOp().getValue(), patchOperation.getPath().getValue(), candidate);
      log.info(message);
      return Optional.of(
          ConstraintViolationHelper.buildConstraintViolation(
              message, Candidate.class, patchOperation.getPath().getValue()));
    }
    try {
      action.accept(candidate, patchOperation.getValue());
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
