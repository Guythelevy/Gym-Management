package gym.management.Sessions.SessionTypes;
import gym.management.Sessions.SessionTypeBase;

public class Ninja extends SessionTypeBase {
    @Override
    public int getCost() {
        return 150;
    }

    @Override
    public int getMaxParticipants() {
        return 5;
    }

    @Override
    public String getTypeName() {
        return "Ninja";
    }
}
