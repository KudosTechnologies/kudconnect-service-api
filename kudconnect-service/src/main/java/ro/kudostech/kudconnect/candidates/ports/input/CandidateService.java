package ro.kudostech.kudconnect.candidates.ports.input;

import java.util.List;
import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.api.server.model.PatchOperationCandidate;

public interface CandidateService {
    Candidate getCandidateById(String userId);
    List<Candidate> getAllCandidates();
    Candidate createCandidate(Candidate candidate);
    void deleteCandidate(String userId);

    void updateCandidate(String candidateId, List<PatchOperationCandidate> patchOperations);
}
