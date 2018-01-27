public abstract class Helper extends Card{

    public Helper(String name, String description, int power){
        super(name, description, 0, power, 0, 0);
    }

    public Helper(String name, String description, int power, int speed){
        this(name, description, power);
        this.setSpeed(speed);
    }
}
