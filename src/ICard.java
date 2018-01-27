import javafx.collections.ObservableList;

public interface ICard{

    // @ ensures \result.length() > 0
    public /*@ pure @*/ String getName();

    // @ ensures \result.length() > 0
    public /*@ pure @*/ String getDescription();

    // @ ensures \result > 0
    public /*@ pure @*/ Integer getAbility();

    // @ ensures \result >= 0
    public /*@ pure @*/ Integer getPower();

    // @ ensures \result >= 0
    public /*@ pure @*/ Integer getSpeed();

    // @ ensures \result >= 0
    public /*@ pure @*/ Integer getProtection();

    // @ ensures \result.size() > 0
    public /*@ pure @*/ ObservableList<Integer> getProperties();

    public /*@ pure @*/ String getType();

    // @ requires ability >= 0
    // @ ensures getAbility() >= 0
    public void setAbility(Integer ability);

    // @ requires power >= 0
    // @ ensures getPower() >= 0
    public void setPower(Integer power);

    // @ requires speed >= 0
    // @ ensures getSpeed() >= 0
    public void setSpeed(Integer speed);

    // @ requires protection >= 0
    // @ ensures getProtection() >= 0
    public void setProtection(Integer protection);
}
