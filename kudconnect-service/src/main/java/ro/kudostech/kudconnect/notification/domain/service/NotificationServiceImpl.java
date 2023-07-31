package ro.kudostech.kudconnect.notification.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.kudostech.kudconnect.common.event.CandidateCdc;
import ro.kudostech.kudconnect.notification.ports.input.NotificationService;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @ApplicationModuleListener
    public void on(CandidateCdc event) {
        if (!StringUtils.hasLength(event.getEmail())) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        log.info("Candidate CDC Event received: {}", event);
    }

    @Override
    public void sendNotification(String message) {
        log.info("Sending notification: {}", message);
    }
}
