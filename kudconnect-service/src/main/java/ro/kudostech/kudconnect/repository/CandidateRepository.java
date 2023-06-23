package ro.kudostech.kudconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.kudostech.kudconnect.domain.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

}
