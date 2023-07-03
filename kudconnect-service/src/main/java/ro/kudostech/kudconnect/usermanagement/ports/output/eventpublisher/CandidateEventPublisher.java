package ro.kudostech.kudconnect.usermanagement.ports.output.eventpublisher;

import ro.kudostech.kudconnect.common.event.CandidateCdc;

public interface CandidateEventPublisher {
    void publishCandidateCDCEvent(CandidateCdc candidateCdc);
}
