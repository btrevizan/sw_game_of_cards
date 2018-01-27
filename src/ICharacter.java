import java.util.LinkedList;

public interface ICharacter{

    // @ ensures \result >= 0
    public /*@ pure @*/ Double getLife();

    // @ ensures \result.length() > 0
    public /*@ pure @*/ String getHelper();

    // @ ensures \result.length() > 0
    public /*@ pure @*/ String getPlace();

    // @ requires helper != null
    // @ ensures getPower() > \old(getPower())
    // @ ensures getSpeed >= \old(getSpeed())
    // @ ensures getHelper().equals(helper.getName())
    public boolean setHelper(Helper helper);

    /* @
            requires n <= getLives() && n > 0
            ensures getLives() == \old(getLives()) - n
        also
            requires n > getLives()
            ensures getLives() == 0
       @
     */
    public void kill(double n);

    // @ requires this != other
    // @ ensures this.getLives() < \old(this.getLives()) || other.getLives() < \old(other.getLives())
    // @ ensures \result.size() == 2
    public LinkedList<Double> attack(Character other);
}
