public abstract class Jedi extends Character{

    public Jedi(String name, String description, int ability, int power, String place){
        super(name, description, ability+15, power+15, place);
    }

}
