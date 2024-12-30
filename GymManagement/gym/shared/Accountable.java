package gym.shared;

public interface Accountable {
    void withdraw(int amount) throws IllegalArgumentException;
    void deposit(int amount);
    int getBalance();
}