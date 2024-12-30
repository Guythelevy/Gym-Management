package gym.management.Sessions.SessionTypes;
import gym.management.Sessions.SessionTypeBase;

public class ThaiBoxing extends SessionTypeBase {
    @Override
    public int getCost() {
        return 100;
    }

    @Override
    public int getMaxParticipants() {
        return 20;
    }

    @Override
    public String getTypeName() {
        return "ThaiBoxing";
    }
}
