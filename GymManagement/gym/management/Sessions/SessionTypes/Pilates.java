package gym.management.Sessions.SessionTypes;
import gym.management.Sessions.SessionTypeBase;

public class Pilates extends SessionTypeBase {
    @Override
    public int getCost() {
        return 60;
    }

    @Override
    public int getMaxParticipants() {
        return 30;
    }

    @Override
    public String getTypeName() {
        return "Pilates";
    }
}
