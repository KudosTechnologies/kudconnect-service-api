package ro.kudostech.kudconnect.usermanagement;

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
import org.springframework.context.ApplicationEventPublisher;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.usermanagement.internal.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.internal.CandidatesServiceImpl;
import ro.kudostech.kudconnect.usermanagement.internal.domain.Candidate;
import ro.kudostech.kudconnect.usermanagement.internal.repository.CandidateRepository;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {
  @Mock private CandidateRepository candidateRepository;
  @Mock private CandidateMapper candidateMapper;
  @Mock private ApplicationEventPublisher applicationEventPublisher;

  @InjectMocks
  private CandidatesServiceImpl cut;

  @Test
  void getCandidateByIdTest() {
    // Arrange
    String userId = "1";
    Candidate candidate = new Candidate();
    CandidateDto candidateDto = new CandidateDto();

    when(candidateRepository.findById(userId)).thenReturn(Optional.of(candidate));
    when(candidateMapper.toCandidateDto(candidate)).thenReturn(candidateDto);

    // Act
    CandidateDto result = cut.getCandidateById(userId);

    // Assert
    assertEquals(candidateDto, result);
    verify(candidateRepository, times(1)).findById(userId);
    verify(candidateMapper, times(1)).toCandidateDto(candidate);
  }

  @Test
  void getAllCandidatesTest() {
    // Arrange
    Candidate candidate1 = new Candidate();
    Candidate candidate2 = new Candidate();
    CandidateDto candidateDto1 = new CandidateDto();
    CandidateDto candidateDto2 = new CandidateDto();

    when(candidateRepository.findAll()).thenReturn(Arrays.asList(candidate1, candidate2));
    when(candidateMapper.toCandidateDto(candidate1)).thenReturn(candidateDto1);
    when(candidateMapper.toCandidateDto(candidate2)).thenReturn(candidateDto2);

    // Act
    List<CandidateDto> result = cut.getAllCandidates();

    // Assert
    assertEquals(2, result.size());
    assertTrue(result.containsAll(Arrays.asList(candidateDto1, candidateDto2)));
    verify(candidateRepository, times(1)).findAll();
    verify(candidateMapper, times(1)).toCandidateDto(candidate1);
    verify(candidateMapper, times(1)).toCandidateDto(candidate2);
  }

  @Test
  void createCandidateTest() {
    // Arrange
    CandidateDto candidateDto = new CandidateDto();
    Candidate candidate = new Candidate();

    when(candidateMapper.toCandidate(candidateDto)).thenReturn(candidate);
    when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);
    when(candidateMapper.toCandidateDto(candidate)).thenReturn(candidateDto);

    // Act
    CandidateDto result = cut.createCandidate(candidateDto);

    // Assert
    assertEquals(candidateDto, result);
    verify(candidateMapper, times(1)).toCandidate(candidateDto);
    verify(candidateRepository, times(1)).save(any(Candidate.class));
    verify(candidateMapper, times(1)).toCandidateDto(candidate);
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
