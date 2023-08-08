package ro.kudostech.kudconnect.usermanagement.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.api.server.model.RegisterUserRequest;
import ro.kudostech.kudconnect.api.server.model.UpdateUserDetailsRequest;
import ro.kudostech.kudconnect.api.server.model.UserDetails;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.UserDetailsRepository;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.model.UserDetailsDbo;
import ro.kudostech.kudconnect.usermanagement.domain.mapper.UserDetailsMapper;
import ro.kudostech.kudconnect.usermanagement.ports.input.UserManagementService;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

  private final UserDetailsRepository userDetailsRepository;
  private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails getUserDetails(String userId) {
      return userDetailsRepository
          .findById(userId)
          .map(userDetailsMapper::toUserDetails)
          .orElseThrow(
              () -> new RuntimeException("UserDetails not found for user with id " + userId));
    }

  @Override
  @Transactional
  public UserDetails getOrCreateUserDetails(String email) {
    return userDetailsRepository
        .findByEmail(email)
        .map(userDetailsMapper::toUserDetails)
        .orElseGet(
            () -> registerUser(new RegisterUserRequest().email(email)));
  }

  @Override
  @Transactional
  public UserDetails registerUser(RegisterUserRequest registerUserRequest) {
    UserDetailsDbo userDetailsDbo = userDetailsMapper.toUserDetailsDbo(registerUserRequest);
    UserDetailsDbo savedUserDetailsDbo = userDetailsRepository.save(userDetailsDbo);
    return userDetailsMapper.toUserDetails(savedUserDetailsDbo);
  }

  @Override
  @Transactional
  public void updateUserDetails(String userId, UpdateUserDetailsRequest userDetails) {
    UserDetailsDbo userDetailsDbo =
        userDetailsRepository
            .findById(userId)
            .orElseThrow(
                () -> new RuntimeException("UserDetails not found for user with id " + userId));
    //    userDetailsDbo.setFirstName(userDetails.getFirstName());
    //    userDetailsDbo.setLastName(userDetails.getLastName());
    //    userDetailsDbo.setEmail(userDetails.getEmail());
    userDetailsDbo.setAvatar(userDetails.getAvatar());
    userDetailsRepository.save(userDetailsDbo);
  }

  @Override
  @Transactional
  public UserDetails addUserDetails(UserDetails userDetails) {
    UserDetailsDbo userDetailsDbo = userDetailsMapper.toUserDetailsDbo(userDetails);
    UserDetailsDbo savedUserDetailsDbo = userDetailsRepository.save(userDetailsDbo);
    return userDetailsMapper.toUserDetails(savedUserDetailsDbo);
  }
}
