package ro.kudostech.kudconnect.usermanagement.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.CandidateRepository;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.model.CandidateDbo;
import ro.kudostech.kudconnect.usermanagement.domain.mapper.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.ports.output.eventpublisher.CandidateEventPublisher;

@ExtendWith(MockitoExtension.class)
class CandidateDboServiceTest {
  @Mock private CandidateRepository candidateRepository;
  @Mock private CandidateMapper candidateMapper;
  @Mock private CandidateEventPublisher candidateEventPublisher;

  @InjectMocks
  private CandidatesServiceImpl cut;

  @Test
  void getCandidateByIdTest() {
    // Arrange
    String userId = "1";
    CandidateDbo candidateDbo = new CandidateDbo();
    Candidate candidate = new Candidate();

    when(candidateRepository.findById(userId)).thenReturn(Optional.of(candidateDbo));
    when(candidateMapper.toCandidate(candidateDbo)).thenReturn(candidate);

    // Act
    Candidate result = cut.getCandidateById(userId);

    // Assert
    assertEquals(candidate, result);
    verify(candidateRepository, times(1)).findById(userId);
    verify(candidateMapper, times(1)).toCandidate(candidateDbo);
  }

  @Test
  void getAllCandidatesTest() {
    // Arrange
    CandidateDbo candidateDbo1 = new CandidateDbo();
    CandidateDbo candidateDbo2 = new CandidateDbo();
    Candidate candidate1 = new Candidate();
    Candidate candidate2 = new Candidate();

    when(candidateRepository.findAll()).thenReturn(Arrays.asList(candidateDbo1, candidateDbo2));
    when(candidateMapper.toCandidate(candidateDbo1)).thenReturn(candidate1);
    when(candidateMapper.toCandidate(candidateDbo2)).thenReturn(candidate2);

    // Act
    List<Candidate> result = cut.getAllCandidates();

    // Assert
    assertEquals(2, result.size());
    assertTrue(result.containsAll(Arrays.asList(candidate1, candidate2)));
    verify(candidateRepository, times(1)).findAll();
    verify(candidateMapper, times(1)).toCandidate(candidateDbo1);
    verify(candidateMapper, times(1)).toCandidate(candidateDbo2);
  }

  @Test
  void createCandidateTest() {
    // Arrange
    Candidate candidate = new Candidate();
    CandidateDbo candidateDbo = new CandidateDbo();

    when(candidateMapper.toCandidateDbo(candidate)).thenReturn(candidateDbo);
    when(candidateRepository.save(any(CandidateDbo.class))).thenReturn(candidateDbo);
    when(candidateMapper.toCandidate(candidateDbo)).thenReturn(candidate);

    // Act
    Candidate result = cut.createCandidate(candidate);

    // Assert
    assertEquals(candidate, result);
    verify(candidateMapper, times(1)).toCandidateDbo(candidate);
    verify(candidateRepository, times(1)).save(any(CandidateDbo.class));
    verify(candidateMapper, times(1)).toCandidate(candidateDbo);
  }

  @Test
  void deleteCandidateTest() {
    // Arrange
    String userId = "1";

    doNothing().when(candidateRepository).deleteById(userId);

    // Act
    cut.deleteCandidate(userId);

    // Assert
    verify(candidateRepository, times(1)).deleteById(userId);
  }
}
