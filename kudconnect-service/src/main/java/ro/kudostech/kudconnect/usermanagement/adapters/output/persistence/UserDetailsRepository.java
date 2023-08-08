package ro.kudostech.kudconnect.usermanagement.adapters.output.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.model.UserDetailsDbo;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsDbo, String> {

  Optional<UserDetailsDbo> findByEmail(String email);
}
