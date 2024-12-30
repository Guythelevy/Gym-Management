package gym.management.Sessions;

import gym.customers.Client;
import gym.management.employee.Instructor;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//מחלקת שיעור ראשית

public class Session {
    private final SessionTypeBase sessionType; // Use dynamically created session type
    private final LocalDateTime date;
    private final ForumType forumType;
    private final Instructor instructor;
    private final List<Client> participants;
    private final int maxParticipants;
    private final int cost;

    //בנאי שיעור ראשי
    public Session(SessionType sessionType, LocalDateTime date, ForumType forumType, Instructor instructor) {
        this.sessionType = sessionType.createInstance(); // Dynamically create the session type
        this.date = date;
        this.forumType = forumType;
        this.instructor = instructor;
        this.participants = new ArrayList<>();
        this.maxParticipants = this.sessionType.getMaxParticipants(); // Use session type properties
        this.cost = this.sessionType.getCost();
    }

    //גטרים וסטרים

    public String getSessionTypeName() {
        return sessionType.getTypeName();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getCost() {
        return cost;
    }

    public List<Client> getParticipants() {
        return new ArrayList<>(participants);
    }

    public void addParticipant(Client client) {
        if (participants.size() < maxParticipants) {
            participants.add(client);
        }
    }

    public void addNotification(String message) {
        for (Client client : participants) {
            client.addNotification(message); // Notify each participant
        }
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Session Type: " + sessionType.getTypeName() +
                " | Date: " + date.format(formatter) +
                " | Forum: " + forumType +
                " | Instructor: " + instructor.getName() +
                " | Participants: " + participants.size() + "/" + maxParticipants;
    }
}
