package gym.management.employee;

import gym.management.Secretary;
import gym.management.employee.Instructor;



public enum EmployeeType {
    SECRETARY("Secretary"),  // מזכירה
    INSTRUCTOR("Instructor"); // מדריך

    private final String formattedName;

    EmployeeType(String formattedName) {
        this.formattedName = formattedName;
    }

    @Override
    public String toString() {
        return formattedName;
    }
}