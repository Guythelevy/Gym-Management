package gym.customers;
import gym.shared.Accountable;
import gym.shared.Gender;
import gym.shared.Notifiable;
import gym.shared.Person;

import java.util.ArrayList;
import java.util.List;


public class Client  implements Notifiable , Accountable {
    private Person person;
    private int balance;
    private List<String> notifications;

    public Client(Person person) {
        this.person=person;
        this.notifications = new ArrayList<>();
    }

    @Override
    public int getBalance() {
        return person.getBalance(); // גישה למאפיין מתוך Person
    }

    // גישה לשם
    public String getName() {
        return person.getName();
    }


    @Override
    public void withdraw(int amount) {
        person.withdraw(amount);
    }

    @Override
    public void deposit(int amount) {
        person.deposit(amount);
    }


    public int getAge() {
        return person.getAge();
    }

    public Gender getGender() {
        return person.getGender();
    }

    public String getDateOfBirth() {
        return person.getFormattedDateOfBirth() ;
    }


    public void addNotification(String message) {
        notifications.add(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public int getId() {
        return  person.getId();
    }



    @Override
    public String toString() {
        return "ID: " + getId() +
                " | Name: " + getName() +
                " | Gender: " + getGender() +
                " | Birthday: " + getDateOfBirth() +
                " | Age: " + getAge() +
                " | Balance: " + getBalance();
    }

    }
