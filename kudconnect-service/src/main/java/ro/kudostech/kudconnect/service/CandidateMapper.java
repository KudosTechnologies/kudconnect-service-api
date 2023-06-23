package ro.kudostech.kudconnect.service;

import java.util.UUID;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.kudostech.kudconnect.api.model.CandidateDto;
import ro.kudostech.kudconnect.domain.Candidate;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {CommonMapper.class})
public interface CandidateMapper {
  default UUID toUserId(Candidate candidate) {
    return UUID.fromString(candidate.getId());
  }

  @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
  CandidateDto toCandidateDto(Candidate candidate);
}
