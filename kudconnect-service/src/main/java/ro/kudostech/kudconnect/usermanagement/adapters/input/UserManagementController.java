package ro.kudostech.kudconnect.usermanagement.adapters.input;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.kudostech.kudconnect.usermanagement.adapters.input.dto.UpdateUserDetailsRequest;
import ro.kudostech.kudconnect.usermanagement.adapters.input.dto.UserDetails;
import ro.kudostech.kudconnect.usermanagement.ports.input.UserManagementService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserManagementController {
  private final UserManagementService userManagementService;

  @GetMapping("/me")
  public ResponseEntity<UserDetails> getLoggedInUserDetails(Principal principal) {
    String userId =
        ((JwtAuthenticationToken) principal).getToken().getClaims().get("user_id").toString();
    UserDetails userDetails = userManagementService.getUserDetails(userId);
    return ResponseEntity.ok(userDetails);
  }

  @PutMapping("/me")
  public ResponseEntity<Void> updateLoggedInUserDetails(
      @RequestBody UpdateUserDetailsRequest updateUserDetailsRequest, Principal principal) {
    String userId =
        ((JwtAuthenticationToken) principal).getToken().getClaims().get("user_id").toString();
    userManagementService.updateUserDetails(userId, updateUserDetailsRequest);
    return ResponseEntity.ok().build();
  }
}
