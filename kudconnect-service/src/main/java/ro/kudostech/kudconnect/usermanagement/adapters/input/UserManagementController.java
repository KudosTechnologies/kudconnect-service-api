package ro.kudostech.kudconnect.usermanagement.adapters.input;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.kudostech.kudconnect.api.server.UserDetailsApi;
import ro.kudostech.kudconnect.api.server.model.UserDetails;
import ro.kudostech.kudconnect.usermanagement.ports.input.UserManagementService;

@RestController
@RequiredArgsConstructor
public class UserManagementController implements UserDetailsApi {
  private final UserManagementService userManagementService;

  @Override
  public ResponseEntity<UserDetails> getCurrentUser() {
    Principal principal = SecurityContextHolder.getContext().getAuthentication();
    String userId =
        ((JwtAuthenticationToken) principal).getToken().getClaims().get("user_id").toString();
    UserDetails userDetails = userManagementService.getUserDetails(userId);
    return ResponseEntity.ok(userDetails);
  }

  @Override
  public ResponseEntity<Void> updateCurrentUser(
      ro.kudostech.kudconnect.api.server.model.UpdateUserDetailsRequest updateUserDetailsRequest) {
    Principal principal = SecurityContextHolder.getContext().getAuthentication();
    String userId =
        ((JwtAuthenticationToken) principal).getToken().getClaims().get("user_id").toString();
    userManagementService.updateUserDetails(userId, updateUserDetailsRequest);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/user-details/{email}")
  public ResponseEntity<UserDetails> getOrRegisterUserDetails(@PathVariable String email) {
    UserDetails userDetails = userManagementService.getOrCreateUserDetails(email);
    return ResponseEntity.ok(userDetails);
  }

}
