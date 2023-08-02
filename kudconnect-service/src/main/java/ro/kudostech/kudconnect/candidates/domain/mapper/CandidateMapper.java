package ro.kudostech.kudconnect.candidates.domain.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.kudostech.kudconnect.api.server.model.Candidate;
import ro.kudostech.kudconnect.candidates.adapters.output.persistence.model.CandidateDbo;
import ro.kudostech.kudconnect.common.CommonMapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {CommonMapper.class})
public interface CandidateMapper {
  @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
  Candidate toCandidate(CandidateDbo candidateDbo);

  @Mapping(source = "id", target = "id", qualifiedByName = "UUIDToString")
  CandidateDbo toCandidateDbo(Candidate candidate);
}
