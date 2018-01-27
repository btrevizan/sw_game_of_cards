import java.util.LinkedList;
import java.util.Observable;

public abstract class Character extends Card implements ICharacter{

    private double life;
    private String place;
    private Helper helper;

    public Character(String name, String description, int ability, String place){
        super(name, description, ability, 0, 0, 0);

        this.life = 100.0;
        this.place = place;
        this.helper = null;
    }

    public Character(String name, String description, int ability, int power, String place){
        this(name, description, ability, place);
        this.setPower(power);
    }

    public Double getLife(){
        return this.life;
    }

    private void setLife(Double life){
        this.life = life;
        this.notifyObservers();
    }

    public String getHelper(){
        if(this.helper == null) return "";
        return this.helper.toString();
    }

    public String getPlace(){
        return this.place;
    }

    public boolean setHelper(Helper helper){
        if(this.helper != null) return false;
        this.helper = helper;

        this.setPower(this.getPower() + this.helper.getPower());
        this.setSpeed(this.getSpeed() + this.helper.getSpeed());
        this.notifyObservers();
        return true;
    }

    private void unsetHelper(){
        if(this.helper == null) return;

        this.setPower(this.getPower() - this.helper.getPower());
        this.setSpeed(this.getSpeed() - this.helper.getSpeed());

        this.helper = null;
    }

    public void kill(double n){
        this.setLife(this.getLife() - n);
        if(this.getLife() < 0) this.setLife(0.0);
    }

    public LinkedList<Double> attack(Character other){
        int ability1 = this.getAbility();
        int power1 = this.getPower();
        int speed1 = this.getSpeed();
        int protection1 = this.getProtection();

        int ability2 = other.getAbility();
        int power2 = other.getPower();
        int speed2 = other.getSpeed();
        int protection2 = other.getProtection();

        Double points1 = this.checkAbility(ability2);
        Double points2 = other.checkAbility(ability1);

        points1 += this.checkPower(power2);
        points2 += other.checkPower(power1);

        points1 += this.checkSpeed(speed2);
        points2 += other.checkSpeed(speed1);

        points1 += this.checkProtection(protection2);
        points2 += other.checkProtection(protection1);

        this.kill(points2);
        other.kill(points1);

        LinkedList<Double> points = new LinkedList<>();
        points.add(points1);
        points.add(points2);

        return points;
    }

    private Double point(int a, int b){
        return (1 - ((double)a / b )) * 30;
    }

    private Double checkAbility(int ability){
        if(this.getAbility() > ability)
            return this.point(ability, this.getAbility());

        return 0.0;
    }

    private Double checkPower(int power){
        if(this.getPower() > power)
            return this.point(power, this.getPower());

        return 0.0;
    }

    private Double checkSpeed(int speed){
        if(this.getSpeed() > speed)
            return this.point(speed, this.getSpeed());

        return 0.0;
    }

    private Double checkProtection(int protection){
        if(this.getProtection() > protection)
            return this.point(protection, this.getProtection());

        return 0.0;
    }
}
