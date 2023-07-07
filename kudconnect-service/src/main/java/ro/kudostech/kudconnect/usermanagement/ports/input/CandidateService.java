package ro.kudostech.kudconnect.usermanagement.ports.input;

import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidateDto;

import java.util.List;

public interface CandidateService {
    CandidateDto getCandidateById(String userId);
    List<CandidateDto> getAllCandidates();
    CandidateDto createCandidate(CandidateDto candidateDto);
    void deleteCandidate(String userId);

    void updateCandidate(String candidateId, List<PatchOperationCandidateDto> patchOperations);
}
