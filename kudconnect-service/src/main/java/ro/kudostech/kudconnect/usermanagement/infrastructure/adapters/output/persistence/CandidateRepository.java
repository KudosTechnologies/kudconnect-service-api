package ro.kudostech.kudconnect.usermanagement.infrastructure.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.kudostech.kudconnect.usermanagement.domain.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {}
