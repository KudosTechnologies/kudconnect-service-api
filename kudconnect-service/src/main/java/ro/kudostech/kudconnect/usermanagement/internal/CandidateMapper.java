package ro.kudostech.kudconnect.usermanagement.internal;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.kudostech.kudconnect.api.server.model.CandidateDto;
import ro.kudostech.kudconnect.usermanagement.internal.domain.Candidate;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {CommonMapper.class})
public interface CandidateMapper {
  @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
  CandidateDto toCandidateDto(Candidate candidate);

  @Mapping(source = "id", target = "id", qualifiedByName = "UUIDToString")
  Candidate toCandidate(CandidateDto candidateDto);
}
