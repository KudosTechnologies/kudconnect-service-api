package ro.kudostech.kudconnect.candidates.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.kudostech.kudconnect.candidates.adapters.output.persistence.model.CandidateDbo;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateDbo, String> {}
