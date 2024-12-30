package gym.management.Sessions;

import gym.customers.Client;

import gym.shared.Gender;

public enum ForumType {
    All, Female, Male, Seniors;

    public String getRestrictionReason(Client client) {
        switch (this) {
            case Female:
                if (client.getGender() != Gender.Female) {
                    return "Client's gender doesn't match the session's gender requirements";
                }
                break;
            case Male:
                if (client.getGender() != Gender.Male) {
                    return "Client's gender doesn't match the session's gender requirements";
                }
                break;
            case Seniors:
                if (client.getAge() < 65) {
                    return "Client doesn't meet the age requirements for this session (Seniors)";
                }
                break;
            default:
                // No restrictions for "All"
                break;
        }
        return null; // אם אין מגבלות
    }
}

