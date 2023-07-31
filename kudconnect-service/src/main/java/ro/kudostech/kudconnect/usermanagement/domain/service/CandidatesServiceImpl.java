package ro.kudostech.kudconnect.usermanagement.domain.service;

import static jakarta.validation.Validation.byProvider;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidate;
import ro.kudostech.kudconnect.common.event.CandidateCdc;
import ro.kudostech.kudconnect.common.exception.PropertyPathProvider;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.CandidateRepository;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.model.CandidateDbo;
import ro.kudostech.kudconnect.usermanagement.domain.mapper.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.ports.input.CandidateService;
import ro.kudostech.kudconnect.usermanagement.ports.output.eventpublisher.CandidateEventPublisher;

@Service
@Slf4j
@RequiredArgsConstructor
public class CandidatesServiceImpl implements CandidateService {

  private final CandidateRepository candidateRepository;
  private final CandidateMapper candidateMapper;
  private final CandidateEventPublisher eventPublisher;
  private final CandidatePatchService candidatePatchService;

  public Candidate getCandidateById(String userId) {
    return candidateRepository
        .findById(userId)
        .map(candidateMapper::toCandidate)
        .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
  }

  public List<Candidate> getAllCandidates() {
    return candidateRepository.findAll().stream().map(candidateMapper::toCandidate).toList();
  }

  @Transactional
  public Candidate createCandidate(Candidate candidate) {
    CandidateDbo candidateDbo = candidateMapper.toCandidateDbo(candidate);
    candidateRepository.save(candidateDbo);
    CandidateCdc candidateCdc =
        CandidateCdc.builder().id(candidateDbo.getId()).email(candidateDbo.getEmail()).build();
    eventPublisher.publishCandidateCDCEvent(candidateCdc);
    return candidateMapper.toCandidate(candidateDbo);
  }

  @Transactional
  @Override
  public void deleteCandidate(String userId) {
    candidateRepository.deleteById(userId);
  }

  @Override
  @Transactional
  public void updateCandidate(String candidateId, List<PatchOperationCandidate> patchOperations) {
    CandidateDbo candidateDbo = candidateRepository
            .findById(candidateId)
            .orElseThrow(() -> new RuntimeException("User with id " + candidateId + " not found"));
    Set<ConstraintViolation<?>> violations = candidatePatchService.patch(candidateDbo, patchOperations);
    try (ValidatorFactory validatorFactory = byProvider(HibernateValidator.class)
            .configure()
            .propertyNodeNameProvider(new PropertyPathProvider())
            .buildValidatorFactory()) {
      violations.addAll(validatorFactory
              .getValidator()
              .validate(candidateDbo));
    }
    if (!violations.isEmpty()) {
      log.info("Could not validate patches for user {}.\nFound the following violations: {}", candidateDbo, violations);
      throw new ConstraintViolationException("violations", violations);
    } else {
      log.debug("Successfully validated patches for user {}.", candidateDbo);
    }
  }
}
