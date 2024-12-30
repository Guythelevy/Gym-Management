package gym.management.Sessions;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.customers.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class LessonRegistrationFactory {

    public static void validateRegistration(Client client, Session session, int currentSecretaryId, int requestingSecretaryId, List<Session> sessions, List<Client> clients)
            throws ClientNotRegisteredException, DuplicateClientException, IllegalStateException {

        Set<String> illegalArgumentErrors = new HashSet<>(); // שימוש ב-Set

        // בדיקת מזכירה נוכחית
        if (currentSecretaryId != requestingSecretaryId) {
            throw new NullPointerException("Only the current secretary can register clients to lessons.");
        }

        // בדיקת null ללקוח
        if (client == null) {
            throw new NullPointerException("Client cannot be null.");
        }
        // בדיקה אם הלקוח רשום במכון
        if (!clients.contains(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        if (session == null) {
            illegalArgumentErrors.add("Failed registration: Session is not in the future");
        } else if (session.getDate().isBefore(LocalDateTime.now())) {
            illegalArgumentErrors.add("Failed registration: Session is not in the future");
        }

        // בדיקה אם השיעור רשום במכון
        if (!sessions.contains(session)) {
            illegalArgumentErrors.add("The session is not registered in the gym.");
        }

        // בדיקה אם השיעור מלא
        if (session != null && session.getParticipants().size() >= session.getMaxParticipants()) {
            illegalArgumentErrors.add("Failed registration: No available spots for session");
        }



        // בדיקה אם יש מגבלות פורום
        String restrictionReason = session.getForumType().getRestrictionReason(client);
        if (restrictionReason != null) {
            illegalArgumentErrors.add("Failed registration: " + restrictionReason);
        }

        // בדיקה אם יש מספיק יתרה
        if (client.getBalance() < session.getCost()) {
            illegalArgumentErrors.add("Failed registration: Client doesn't have enough balance");
        }

        // אם נמצאו שגיאות מסוג IllegalArgumentException, לזרוק אותן
        if (!illegalArgumentErrors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", illegalArgumentErrors));
        }

        // בדיקה אם הלקוח כבר רשום לשיעור
        if (session.getParticipants().contains(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }
    }
}