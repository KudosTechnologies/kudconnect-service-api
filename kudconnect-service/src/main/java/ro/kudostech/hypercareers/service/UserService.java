package ro.kudostech.hypercareers.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.kudostech.hypercareers.api.model.UserDto;
import ro.kudostech.hypercareers.domain.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final String STATIC_USER_ID = "c2135746-e634-4283-b352-f6179c1abd02";
  private final String COMPANY_ID = "f8116e5e-3a84-45ab-ae58-da4d0390bf03";

  private final UserMapper userMapper;

  public UserDto getUserById(String userId) {
    if (userId.equals(STATIC_USER_ID)) {
      User user = User.builder().id(STATIC_USER_ID).companyId(COMPANY_ID).build();
      return userMapper.toUserDto(user);
//        return new UserDto(UUID.fromString(STATIC_USER_ID), UUID.fromString(COMPANY_ID));
    } else {
      throw new RuntimeException("User with id " + userId + " not found");
    }
  }
}
