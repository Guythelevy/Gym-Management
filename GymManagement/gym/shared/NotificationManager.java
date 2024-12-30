package gym.shared;

import gym.shared.Notifiable;

public class NotificationManager {
    public void sendNotification(Notifiable target, String message) {
        target.addNotification(message);
    }

}
