package ro.kudostech.kudconnect.notification.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.kudostech.kudconnect.notification.NotificationService;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String message) {
        log.info("Sending notification: {}", message);
    }
}
