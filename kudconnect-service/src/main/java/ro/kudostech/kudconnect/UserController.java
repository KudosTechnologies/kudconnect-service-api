package ro.kudostech.kudconnect;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.kudostech.kudconnect.api.UsersApi;
import ro.kudostech.kudconnect.api.model.UserDto;
import ro.kudostech.kudconnect.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi{
    private final UserService userService;
    @Override
    public ResponseEntity<UserDto> getUsers() {
        return ResponseEntity.ok(userService.getUserById("c2135746-e634-4283-b352-f6179c1abd02"));
    }
}
