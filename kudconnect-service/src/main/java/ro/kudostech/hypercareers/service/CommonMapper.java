package ro.kudostech.hypercareers.service;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class CommonMapper {

  @Named("stringToUUID")
  UUID stringToUUID(String id) {
    return id == null ? null : UUID.fromString(id);
  }
}
