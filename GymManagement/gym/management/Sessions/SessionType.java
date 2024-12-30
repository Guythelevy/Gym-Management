package gym.management.Sessions;

import gym.management.Sessions.SessionTypes.Ninja;
import gym.management.Sessions.SessionTypes.MachinePilates;
import gym.management.Sessions.SessionTypes.Pilates;
import gym.management.Sessions.SessionTypes.ThaiBoxing;

//מחלקה המלכדת את סוג השיעור

public enum SessionType {
    ThaiBoxing(ThaiBoxing.class),
    MachinePilates(MachinePilates.class),
    Pilates(Pilates.class),
    Ninja(Ninja.class);

    private final Class<? extends SessionTypeBase> typeClass;

    SessionType(Class<? extends SessionTypeBase> typeClass) {
        this.typeClass = typeClass;
    }

    public SessionTypeBase createInstance() {
        try {
            return typeClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance for session type: " + this.name(), e);
        }
    }
}
