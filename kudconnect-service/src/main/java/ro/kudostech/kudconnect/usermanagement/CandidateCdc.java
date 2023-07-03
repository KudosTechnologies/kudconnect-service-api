package ro.kudostech.kudconnect.usermanagement;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateCdc {
  String id;
  String firstName;
  String lastName;
  String email;
}
