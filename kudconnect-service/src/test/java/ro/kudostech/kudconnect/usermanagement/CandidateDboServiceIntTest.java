package ro.kudostech.kudconnect.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.modulith.test.ApplicationModuleTest;
import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.usermanagement.domain.mapper.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.domain.service.CandidatesServiceImpl;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.model.CandidateDbo;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.CandidateRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This class is an example of how to use @ApplicationModuleTest annotation that comes with spring-modulith.
 * ref link: <a href="https://docs.spring.io/spring-modulith/docs/current/reference/html/#testing">...</a>
 */
@ApplicationModuleTest
class CandidateDboServiceIntTest {

  @MockBean private CandidateRepository candidateRepository;
  @MockBean private CandidateMapper candidateMapper;

  @Autowired private CandidatesServiceImpl cut;

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
}
