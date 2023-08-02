package ro.kudostech.kudconnect.usermanagement.domain.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.kudostech.kudconnect.common.CommonMapper;
import ro.kudostech.kudconnect.usermanagement.adapters.input.dto.UserDetails;
import ro.kudostech.kudconnect.usermanagement.adapters.output.persistence.model.UserDetailsDbo;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CommonMapper.class})
public interface UserDetailsMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    UserDetails toUserDetails(UserDetailsDbo candidateDbo);

    @Mapping(source = "id", target = "id", qualifiedByName = "UUIDToString")
    UserDetailsDbo toUserDetailsDbo(UserDetails userDetails);
    
}
