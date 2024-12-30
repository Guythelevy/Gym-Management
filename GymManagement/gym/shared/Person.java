package gym.shared;



import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Person implements Accountable {
    private String name;
    private int balance;
    private Gender gender;
    private LocalDate dateOfBirth;

    private static int idCounter = 1111; // מזהה ראשוני לכל אדם
    private final int id;

    public Person(String name, int balance, Gender gender, String dateOfBirth) {
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        this.dateOfBirth = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public void withdraw(int amount) {
        if (getBalance() < amount) {

            return;
        }
        setBalance(getBalance() - amount);
    }

    @Override
    public void deposit(int amount) {
        if (amount < 0) {

            return;
        }
        setBalance(getBalance() + amount);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public String getFormattedDateOfBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateOfBirth.format(formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Person{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", gender=" + gender +
                ", dateOfBirth=" + getFormattedDateOfBirth()  +
                '}';
    }


    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }



}
