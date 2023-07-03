package ro.kudostech.kudconnect.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.modulith.test.ApplicationModuleTest;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.notification.ports.input.NotificationService;
import ro.kudostech.kudconnect.usermanagement.domain.mapper.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.domain.service.CandidatesServiceImpl;
import ro.kudostech.kudconnect.usermanagement.domain.model.Candidate;
import ro.kudostech.kudconnect.usermanagement.infrastructure.adapters.output.persistence.CandidateRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This class is an example of how to use @ApplicationModuleTest annotation that comes with spring-modulith.
 * ref link: https://docs.spring.io/spring-modulith/docs/current/reference/html/#testing
 */
@ApplicationModuleTest
class CandidateServiceIntTest {

  @MockBean private CandidateRepository candidateRepository;
  @MockBean private CandidateMapper candidateMapper;

  @Autowired private CandidatesServiceImpl cut;

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
}
