package gym.management.employee;

import gym.Exception.InstructorNotQualifiedException;
import gym.shared.Person;
import gym.management.Sessions.SessionType;

import java.util.List;

public class Instructor extends Employee {

    private final List<SessionType> certifiedClasses;
    private int workedHours;

    public Instructor(Person person, int salaryPerHour, List<SessionType> certifiedClasses) {
        super(person, salaryPerHour,EmployeeType.INSTRUCTOR);
        this.certifiedClasses = certifiedClasses;
        this.workedHours = 0;
    }

    public void addWorkedHours() {
        workedHours++;
    }


    @Override
    public int calculateSalary() {
        return getSalary() * getWorkedHours();
    }

    public int getWorkedHours() {return this.workedHours;}
    public void setWorkedHours(int hours){
        this.workedHours=hours;
    }
    public List<SessionType> getCertifiedClasses() {
        return certifiedClasses;
    }

    public boolean Qualified (Instructor instructor, SessionType type) throws InstructorNotQualifiedException {
        if (!instructor.getCertifiedClasses().contains(type))
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");

        return true;
    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                " | Name: " + getName() +
                " | Gender: " + getPerson().getGender() +
                " | Birthday: " + getPerson().getFormattedDateOfBirth()  +
                " | Age: " + getAge() +
                " | Balance: " + getPerson().getBalance() +
                " | Role: Instructor" +
                " | Salary per Hour: " + this.getSalary() +
                " | Certified Classes: " + String.join(", ", certifiedClasses.stream()
                .map(Enum::name)
                .toList());
    }

}
