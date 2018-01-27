public class Place extends Card{

    public Place(String name, String description, int protection){
        super(name, description, 0, 0, 0, protection);
    }

    public boolean isPlace(Character character){
        return this.getName().equals(character.getPlace());
    }
}
