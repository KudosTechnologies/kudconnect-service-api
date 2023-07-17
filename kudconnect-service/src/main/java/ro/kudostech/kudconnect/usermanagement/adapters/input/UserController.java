package ro.kudostech.kudconnect.usermanagement.adapters.input;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping()
    public ResponseEntity<String> getUser(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("name");
        String userEmail = (String) token.getTokenAttributes().get("email");
        List<String> roles = ((JwtAuthenticationToken) principal).getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok("Hello User \nUser Name : " + userName + "\nUser Email : " + userEmail + "\nRoles :" + roles);
    }
}
