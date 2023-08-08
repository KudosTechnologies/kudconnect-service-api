package ro.kudostech.kudconnect.usermanagement.ports.input;


import ro.kudostech.kudconnect.api.server.model.RegisterUserRequest;
import ro.kudostech.kudconnect.api.server.model.UpdateUserDetailsRequest;
import ro.kudostech.kudconnect.api.server.model.UserDetails;

public interface UserManagementService {

    UserDetails getUserDetails(String userId);

    UserDetails getOrCreateUserDetails(String email);

    UserDetails registerUser(RegisterUserRequest registerUserRequest);

    void updateUserDetails(String userId, UpdateUserDetailsRequest userDetails);

    UserDetails addUserDetails(UserDetails userDetails);

}
