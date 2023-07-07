package ro.kudostech.kudconnect.usermanagement.adapters.output.eventpublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ro.kudostech.kudconnect.common.event.CandidateCdc;
import ro.kudostech.kudconnect.usermanagement.ports.output.eventpublisher.CandidateEventPublisher;

@Component
@RequiredArgsConstructor
public class CandidateEventPublisherAdapter implements CandidateEventPublisher {
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void publishCandidateCDCEvent(CandidateCdc candidateCdc) {
    eventPublisher.publishEvent(candidateCdc);
  }
}
