package ro.kudostech.kudconnect.usermanagement.adapters.input.dto;

import lombok.Value;

@Value
public class UpdateUserDetailsRequest {
  String username;
  String email;
  String firstName;
  String lastName;
  String avatar;
}
