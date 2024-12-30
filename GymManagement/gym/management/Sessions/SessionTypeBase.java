package gym.management.Sessions;

//מחלקה עם מתודות הכרחיות לכל שיעור
public abstract class SessionTypeBase {
    public abstract int getCost();
    public abstract int getMaxParticipants();
    public abstract String getTypeName();
}
