package ro.kudostech.kudconnect.usermanagement.ports.input;

import ro.kudostech.kudconnect.usermanagement.adapters.input.dto.RegisterUserRequest;
import ro.kudostech.kudconnect.usermanagement.adapters.input.dto.UpdateUserDetailsRequest;
import ro.kudostech.kudconnect.usermanagement.adapters.input.dto.UserDetails;

public interface UserManagementService {

    UserDetails getUserDetails(String userId);

    void registerUser(RegisterUserRequest registerUserRequest);

    void updateUserDetails(String userId, UpdateUserDetailsRequest userDetails);

}
