# GymManagement 🏋️‍♀️💼

**GymManagement** is a robust system for managing gym operations, including client registrations, session scheduling, employee oversight, and notifications. It simplifies complex workflows and ensures smooth operations for gyms of all sizes.

---

## 🚀 Features

- **Client Management** 👥  
  - Register and manage clients.
  - Handle notifications for sessions and gym-wide updates.

- **Session Management** 🕒  
  - Create and manage dynamic session types (e.g., Ninja, Pilates).
  - Enforce constraints like age, gender, and availability.

- **Employee Management** 👩‍🏫  
  - Manage roles like Instructors and Secretaries.
  - Track instructor certifications for specific session types.

- **Notifications** 🔔  
  - Notify participants about session updates.
  - Broadcast gym-wide announcements.

- **Exception Handling** ⚠️  
  - Handle edge cases like duplicate client registrations, unauthorized actions, and session overbooking.

---

## 📂 Project Structure


GymManagement/

├── management/

│   ├── Sessions/

│   │   ├── Session.java               # Represents a gym session

│   │   ├── SessionFactory.java        # Factory for creating sessions

│   │   ├── SessionType.java           # Enum mapping to session types

│   │   ├── ForumType.java             # Enum for session forums

│   │   ├── LessonRegistrationFactory.java # Handles lesson registrations

│   │   ├── SessionTypeBase.java       # Abstract base class for session types

│   │   ├── SessionTypes/              # Folder for specific session types

│   │   │   ├── Ninja.java             # Ninja session type

│   │   │   ├── MachinePilates.java    # Machine Pilates session type

│   │   │   ├── Pilates.java           # Pilates session type

│   │   │   ├── ThaiBoxing.java        # Thai Boxing session type

│   ├── employee/

│   │   ├── Employee.java              # Represents a gym employee

│   │   ├── EmployeeType.java          # Enum for employee roles

│   │   ├── Instructor.java            # Represents an instructor

│   ├── Secretary.java                 # Manages gym operations

│   ├── Gym.java                       # Core class for gym functionality

├── customers/

│   ├── Client.java                    # Represents a gym client

│   ├── ClientFactory.java             # Factory for creating and managing clients

├── shared/

│   ├── Accountable.java               # Interface for tracking financials

│   ├── Gender.java                    # Enum for gender classification

│   ├── Notifiable.java                # Interface for handling notifications

│   ├── NotificationManager.java       # Centralized notification manager

│   ├── Person.java                    # Base class for all individuals

├── exceptions/

│   ├── DuplicateClientException.java  # Thrown when a duplicate client is registered

│   ├── InstructorNotQualifiedException.java # Thrown when an instructor lacks proper certification

│   ├── NoAvailableSpotsException.java # Thrown when a session is fully booked

│   ├── UnauthorizedActionException.java # Thrown for unauthorized actions



---

## 📊 Session Types

The following session types are implemented in the `SessionTypes` folder:

- **Ninja** 🥷  
  - Max participants: 5  
  - Cost: 150  

- **Machine Pilates** 🧘‍♂️  
  - Max participants: 10  
  - Cost: 80  

- **Pilates** 🧘‍♀️  
  - Max participants: 30  
  - Cost: 60  

- **Thai Boxing** 🥊  
  - Max participants: 20  
  - Cost: 100  

---

### There is a main example file included

## 🛠️ Usage
```java
### Register a Client

Client client1 = ClientFactory.createClient("John Doe", Gender.Male, "25-12-1995");

### Create a Session

Session s1 = gymSecretary.addSession(SessionType.Pilates, "23-01-2025 10:00", ForumType.All, instructor1);
Add a Client to a Session

try {
    s1.addParticipant(client1);
} catch (NoAvailableSpotsException e) {
    System.out.println("Registration failed: " + e.getMessage());
}
Notify Participants
java
Copy code
NotificationManager.sendToSession(s1, "The session will start 10 minutes late.");

