package ro.kudostech.kudconnect.usermanagement.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.kudostech.kudconnect.usermanagement.internal.domain.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

}
