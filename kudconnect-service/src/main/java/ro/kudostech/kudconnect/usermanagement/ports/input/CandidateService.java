package ro.kudostech.kudconnect.usermanagement.ports.input;

import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidate;

import java.util.List;

public interface CandidateService {
    Candidate getCandidateById(String userId);
    List<Candidate> getAllCandidates();
    Candidate createCandidate(Candidate candidate);
    void deleteCandidate(String userId);

    void updateCandidate(String candidateId, List<PatchOperationCandidate> patchOperations);
}
