package ro.kudostech.kudconnect.service;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.kudostech.kudconnect.api.model.UserDto;
import ro.kudostech.kudconnect.domain.User;

import java.util.UUID;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {CommonMapper.class})
public interface UserMapper {
  default UUID toUserId(User user) {
    return UUID.fromString(user.getId());
  }

  @Mapping(source = "id", target = "userId", qualifiedByName = "stringToUUID")
  @Mapping(source = "companyId", target = "companyId", qualifiedByName = "stringToUUID")
  UserDto toUserDto(User user);
}
