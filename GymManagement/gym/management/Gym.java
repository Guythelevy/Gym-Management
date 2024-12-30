package gym.management;


import gym.customers.Client;
import gym.management.employee.Employee;
import gym.management.employee.Instructor;
import gym.management.Sessions.Session;
import gym.shared.Person;

import java.util.ArrayList;
import java.util.List;



public class Gym {

     int gymBalance;
    private static Gym instance;
    private String name;
     Secretary currentSecretary;
     List<String> actionsHistory ;

     ArrayList<Session> sessions;
     ArrayList<Client> clients;
   ArrayList<Employee> employees ;

    private boolean isMainInitialized = false;

    private Gym() {
        sessions = new ArrayList<>();
        clients = new ArrayList<>();
        employees = new ArrayList<>();
        actionsHistory = new ArrayList<>();
        gymBalance = 0;
    }


    public static Gym getInstance() {
        if (instance == null) {
            if (!isCalledByMain()) {
                throw new SecurityException("Gym can only be instantiated from the main method.");
            }
            instance = new Gym();
            instance.isMainInitialized = true;
        }
        return instance;
    }

    private static boolean isCalledByMain() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getMethodName().equals("main")) {
                return true;
            }
        }
        return false;
    }


    public void setName(String name) {
        this.name = name;
    }


    //הגדרת מזכירה לחדר כושר

    public void setSecretary(Person person, int salary) {

        //בדיקת שגיאות הכרחיות
        if (!isMainInitialized) {
            throw new SecurityException("Access to Gym is restricted to the main method.");
        }
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive.");
        }


        if (this.currentSecretary != null) {
            employees.remove(this.currentSecretary);
            this.currentSecretary = null;
        }


        //יצירת מזכירה
        Secretary newSecretary = new Secretary(person, salary);
        employees.add(newSecretary); //הוספה לעובדים


        actionsHistory.add("A new secretary has started working at the gym: " + newSecretary.getName());


        this.currentSecretary = newSecretary;
    }

    public Secretary getSecretary() {
        return currentSecretary;
    }

    void update(String action, Object... data) {
        switch (action) {
            case "addClient":
                if (data.length == 1 && data[0] instanceof Client) {
                    Client newClient = (Client) data[0];
                    clients.add(newClient);
                    actionsHistory.add("Registered new client: " + newClient.getName());
                }
                break;

            case "removeClient":
                if (data.length == 1 && data[0] instanceof Client) {
                    Client clientToRemove = (Client) data[0];
                    if (clients.contains(clientToRemove)) {
                        clients.remove(clientToRemove);
                        actionsHistory.add("Unregistered client: " + clientToRemove.getName());
                    }
                }
                break;
            case "addSession":
                if (data.length == 2 && data[0] instanceof Session && data[1] instanceof Instructor) {
                    Session newSession = (Session) data[0];
                    Instructor instructor = (Instructor) data[1];

                    if (!sessions.contains(newSession)) { // בדיקה אם השיעור כבר קיים
                        sessions.add(newSession);

                        if (employees.contains(instructor)) {
                            instructor.addWorkedHours(); // עדכון שעות המדריך
                        }

                        actionsHistory.add("Created new session: " + newSession.getSessionTypeName() +
                                " on " + newSession.getDate() +
                                " with instructor: " + instructor.getName());
                    }
                }
                break;


            case "addClientToSession":
                if (data.length == 2 && data[0] instanceof Client && data[1] instanceof Session) {
                    Client client = (Client) data[0];
                    Session session = (Session) data[1];

                    gymBalance += session.getCost();
                    actionsHistory.add("Registered client: " + client.getName() +
                            " to session: " + session.getSessionTypeName() +
                            " on " + session.getDate() +
                            " for price: " + session.getCost());
                }
                break;

            case "addInstructor":
                if (data.length == 1 && data[0] instanceof Instructor) {
                    Instructor newInstructor = (Instructor) data[0];
                    employees.add(newInstructor);
                    actionsHistory.add("Hired new instructor: " + newInstructor.getName() +
                            " with salary per hour: " + newInstructor.getSalary());
                }
                break;

            case "validationError", "notification":
                if (data.length == 1 && data[0] instanceof String) {
                    String errorMessage = (String) data[0];

                    actionsHistory.add(errorMessage);
                }
                break;
            case "paySalaries":
                if (data.length == 1 && data[0] instanceof Double) {
                    double totalSalaries = (Double) data[0];
                    gymBalance -= totalSalaries;
                    actionsHistory.add("Salaries have been paid to all employees");
                }
                break;

            default:
                System.out.println("Gym updated: Unknown action");
        }
    }
    ArrayList<Session> getSessions() {
        return this.sessions;
    }
    ArrayList<Employee> getEmployees() {
        return this.employees;
    }
     List<Client> getClients() {
        return this.clients;
    }
    @Override


    public String toString() { //פקודת הדפסה
        StringBuilder sb = new StringBuilder();
        sb.append("Gym Name: ").append(name).append("\n");
        sb.append("Gym Secretary: ").append(currentSecretary != null ? currentSecretary : "None").append("\n");
        sb.append("Gym Balance: ").append(gymBalance).append("\n");

        // Clients Data
        sb.append("\nClients Data:");
        for (int i = 0; i < clients.size(); i++) {
            sb.append("\n").append(clients.get(i));
        }

        // Employees Data
        sb.append("\n\nEmployees Data:");
        for (int i = 0; i < employees.size(); i++) {
            sb.append("\n").append(employees.get(i));
        }

        // Sessions Data
        sb.append("\n\nSessions Data:");
        for (int i = 0; i < sessions.size(); i++) {
            sb.append("\n").append(sessions.get(i));
        }

        return sb.toString().trim(); 
    }

    }


