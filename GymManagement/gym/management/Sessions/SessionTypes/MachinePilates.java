package gym.management.Sessions.SessionTypes;
import gym.management.Sessions.SessionTypeBase;

public class MachinePilates extends SessionTypeBase {
    @Override
    public int getCost() {
        return 80;
    }

    @Override
    public int getMaxParticipants() {
        return 10;
    }

    @Override
    public String getTypeName() {
        return "MachinePilates";
    }
}
