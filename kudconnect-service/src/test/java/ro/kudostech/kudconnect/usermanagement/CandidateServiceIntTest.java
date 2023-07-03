package ro.kudostech.kudconnect.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.modulith.test.ApplicationModuleTest;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.notification.NotificationService;
import ro.kudostech.kudconnect.usermanagement.internal.CandidateMapper;
import ro.kudostech.kudconnect.usermanagement.internal.CandidatesServiceImpl;
import ro.kudostech.kudconnect.usermanagement.internal.domain.Candidate;
import ro.kudostech.kudconnect.usermanagement.internal.repository.CandidateRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ApplicationModuleTest
public class CandidateServiceIntTest {

    @MockBean
    private CandidateRepository candidateRepository;
    @MockBean private CandidateMapper candidateMapper;
    @MockBean private NotificationService notificationService;

    @Autowired
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

}
