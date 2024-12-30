package gym.management.employee;

import gym.shared.Person;
import gym.management.Secretary;

public abstract class Employee {
    private Person person;       // העובד עצמו
    private int salary;          // השכר של העובד
    private EmployeeType type;   // סוג העובד (מזכירה, מאמן וכו')

    public Employee(Person person, int salary, EmployeeType type) {
        this.person = person;
        this.salary = salary;
        this.type = type;
    }

    public abstract int calculateSalary();

    public void resetWorkHours() {
        // פעולה זו מיועדת למדריכים בלבד ולכן לא תעשה דבר כאן
    }

    public void deposit(int amount) {
        person.deposit(amount);
    }

    // גטרים וסטרים
    public Person getPerson() {
        return person;
    }

    public int getSalary() {
        return salary;
    }

    public EmployeeType getType() {
        return type;
    }

    public String getName() {
        return person.getName();
    }

    public int getAge() {
        return person.getAge();
    }

    public int getId() {
        return person.getId();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getId())
                .append(" | Name: ").append(getName())
                .append(" | Gender: ").append(person.getGender())
                .append(" | Birthday: ").append(person.getFormattedDateOfBirth())
                .append(" | Age: ").append(getAge())
                .append(" | Balance: ").append(person.getBalance())
                .append(" | Role: ").append(type);

        if (type == EmployeeType.INSTRUCTOR && this instanceof Instructor) {
            Instructor instructor = (Instructor) this;
            sb.append(" | Salary per Hour: ").append(instructor.getSalary())
                    .append(" | Certified Classes: ").append(instructor.getCertifiedClasses());
        } else if (type == EmployeeType.SECRETARY && this instanceof Secretary) {
            sb.append(" | Salary per Month: ").append(getSalary());
        }

        return sb.toString();
    }

}
