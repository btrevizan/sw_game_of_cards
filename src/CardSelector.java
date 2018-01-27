public class CardSelector{

    private static CardSelector instance = null;

    public static CardSelector getInstance(){
        if(instance == null)
            instance = new CardSelector();

        return instance;
    }

    public Card createCard(String[] info){
        if(info[1].equals("Place"))
            return new Place(info[2], info[3], Integer.parseInt(info[4]));

        if(info[1].equals("Weapon"))
            return new Weapon(info[2], info[3], Integer.parseInt(info[4]));

        if(info[1].equals("Vehicle"))
            return new Vehicle(info[2], info[3], Integer.parseInt(info[4]), Integer.parseInt(info[5]));

        if(info[1].equals("Creature"))
            return new Creature(info[2], info[3], Integer.parseInt(info[4]), info[5]);

        if(info[1].equals("Fighter"))
            return new Fighter(info[2], info[3], Integer.parseInt(info[4]), info[5]);

        if(info[1].equals("Knight"))
            return new Knight(info[2], info[3], Integer.parseInt(info[4]), Integer.parseInt(info[5]), info[6]);

        if(info[1].equals("Master"))
            return new Master(info[2], info[3], Integer.parseInt(info[4]), Integer.parseInt(info[5]), info[6]);

        return null;
    }
}
