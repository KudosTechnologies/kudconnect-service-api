package ro.kudostech.kudconnect.candidates.adapters.output.eventpublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ro.kudostech.kudconnect.candidates.ports.output.eventpublisher.CandidateEventPublisher;
import ro.kudostech.kudconnect.common.event.CandidateCdc;

@Component
@RequiredArgsConstructor
public class CandidateEventPublisherAdapter implements CandidateEventPublisher {
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void publishCandidateCDCEvent(CandidateCdc candidateCdc) {
    eventPublisher.publishEvent(candidateCdc);
  }
}
