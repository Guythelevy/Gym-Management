package gym.management.Sessions;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import gym.Exception.*;
import gym.management.Gym;
import gym.management.employee.Employee;
import gym.management.employee.Instructor;


//פאקטורי ליצירת שיעור
public class SessionFactory {
    //בנאי ליצירת שיעור
    public static Session createSession(SessionType type, String date, ForumType forum, Instructor instructor,
                                        int currentSecretaryId, int requestingSecretaryId,
                                        List<Employee> employees) throws InstructorNotQualifiedException {

        Session newSession = null;
        //בדיקת מזכירה
        try {

            if (currentSecretaryId != requestingSecretaryId) {
                throw new NullPointerException("Error: Only the current secretary can create a new session.");
            }

            //בדיקה האם העובד רשום כעובד בחדר כושר
            if (!employees.contains(instructor)) {
                throw new IllegalArgumentException("Error: The instructor is not an employee of the gym.");
            }

            //שגיאת מורה לא מוסמך
            if (!instructor.getCertifiedClasses().contains(type)) {
                throw new InstructorNotQualifiedException(
                        "Error: Instructor is not qualified to conduct this session type.");
            }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime parsedDate = LocalDateTime.parse(date, formatter);

            if (parsedDate.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Failed registration: Session is not in the future");
            }


            newSession = new Session(type, parsedDate, forum, instructor);


        } catch (IllegalArgumentException | InstructorNotQualifiedException e) {
            // בדיקה אם השגיאה נובעת מכך שהתאריך אינו בעתיד
            if (e.getMessage().equals("Failed registration: Session is not in the future")) {

                // ניסיון ליצור את השיעור למרות השגיאה בתאריך
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime parsedDate = LocalDateTime.parse(date, formatter);

                return new Session(type, parsedDate, forum, instructor);

            } else {
                throw e; // שגיאה אחרת תיזרק החוצה
            }
        }

        return newSession;
    }

}
