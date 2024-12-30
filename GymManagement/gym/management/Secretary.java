package gym.management;
import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.customers.ClientFactory;
import gym.management.Sessions.*;
import gym.management.employee.Employee;
import gym.management.employee.EmployeeType;
import gym.management.employee.Instructor;
import gym.management.Sessions.LessonRegistrationFactory;
import gym.shared.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Secretary extends Employee {


    private Gym gym;

    public Secretary(Person person, int salary) {
        super(person, salary, EmployeeType.SECRETARY);
        this.gym = Gym.getInstance();

    }

    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
        try {
            Client client = ClientFactory.createClient(
                    person, gym.getSecretary().getId(), this.getId(), gym.getClients()
            );
            gym.update("addClient", client);
            return client;

        } catch (DuplicateClientException | InvalidAgeException | NullPointerException e) {
            throw e;
        }
    }


    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        try {

            boolean canUnregister = ClientFactory.canUnregisterClient(
                    client,
                    gym.getSecretary().getId(),
                    this.getId(),
                    gym.getClients()
            );

            if (canUnregister) {
                gym.update("removeClient", client);
            }

        } catch (IllegalArgumentException | ClientNotRegisteredException | NullPointerException e) {
            throw e;
        }
    }


    public Session addSession(SessionType type, String dateTime, ForumType forum, Instructor instructor) throws InstructorNotQualifiedException {
        try {
            Session session = SessionFactory.createSession(
                    type, dateTime, forum, instructor,
                    gym.getSecretary().getId(), this.getId(), gym.getEmployees()

            );

            gym.update("addSession", session, instructor);
            return session;

        } catch (InstructorNotQualifiedException | NullPointerException e) {
            throw e;

        } catch (IllegalArgumentException e) {

            gym.update("validationError", "Failed to create session: " + e.getMessage());
        }
        return null;
    }

    public void registerClientToLesson(Client client, Session session)
            throws DuplicateClientException, ClientNotRegisteredException {

        try {
            LessonRegistrationFactory.validateRegistration(
                    client, session, gym.getSecretary().getId(), this.getId(),
                    gym.getSessions(), gym.getClients()
            );

            client.withdraw(session.getCost());
            session.addParticipant(client);

            gym.update("addClientToSession", client, session);

        } catch (IllegalArgumentException e) {
            gym.update("validationError", e.getMessage()); // עדכון GYM רק עם השגיאה
        } catch (DuplicateClientException | ClientNotRegisteredException | NullPointerException e) {
            throw e; // זריקת השגיאות האחרות
        }
    }





    public Instructor hireInstructor(Person person, int salaryPerHour, ArrayList<SessionType> certifiedClasses) {

        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }
        Instructor newInstructor = new Instructor(person, salaryPerHour, certifiedClasses);
        gym.update("addInstructor", newInstructor);

        return newInstructor;
    }
    @Override
    public int calculateSalary() {
        return getSalary();
    }

    public void paySalaries() {
        double totalSalaries = 0;

        for (Employee employee : gym.getEmployees()) {
            int salary = employee.calculateSalary();
            totalSalaries += salary;
            employee.deposit(salary);
            employee.resetWorkHours();
        }

        gym.update("paySalaries", totalSalaries);
    }

    public void printActions() {

        for (String action : gym.actionsHistory) {
            System.out.println(action);
        }
    }

    public void notify(Session session, String message) {
        if (session == null || message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Session or message cannot be null or empty.");
        }
        session.addNotification(message);

        Gym gym = Gym.getInstance();
        gym.update("notification", "A message was sent to everyone registered for session " +
                session.getSessionTypeName() + " on " + session.getDate() + " : " + message);
    }

    public void notify(String target, String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty.");
        }

        Gym gym = Gym.getInstance();

        if (target != null && !target.isEmpty()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime parsedDate;

            try {
                // המרה מתאריך בפורמט dd-MM-yyyy ל-LocalDateTime
                parsedDate = LocalDateTime.parse(target + " 00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format. Please use 'dd-MM-yyyy'.");
            }

            Set<Client> notifiedClients = new HashSet<>();
            boolean notificationSent = false;

            for (Session session : gym.getSessions()) {
                if (session.getDate().toLocalDate().equals(parsedDate.toLocalDate())) {
                    for (Client client : session.getParticipants()) {
                        if (notifiedClients.add(client)) {
                            client.addNotification(message);
                        }
                    }
                    notificationSent = true;
                }
            }

            if (notificationSent) {
                // המרה לפורמט yyyy-MM-dd עבור ההודעה
                String formattedDate = parsedDate.format(outputFormatter);
                String formattedMessage = "A message was sent to everyone registered for a session on " +
                        formattedDate + " : " + message;
                gym.update("notification", formattedMessage);
            }
        } else if (target == null) {
            for (Client client : gym.getClients()) {
                client.addNotification(message);
            }
            String formattedMessage = "A message was sent to all gym clients: " + message;
            gym.update("notification", formattedMessage);
        } else {
            throw new IllegalArgumentException("Invalid target type. Must be a date (String) or null.");
        }
    }




    public void notify(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty.");
        }

        for (Client client : gym.getClients()) {
            client.addNotification(message);
        }

        gym.update("notification", "A message was sent to all gym clients: " + message);
    }



}
