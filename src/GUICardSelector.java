public class GUICardSelector{

    private static GUICardSelector instance = null;

    public static GUICardSelector getInstance(){
        if(instance == null)
            instance = new GUICardSelector();

        return instance;
    }

    public GUICard createGUICard(Card card){
        if(card.isPlace()) return new GUIPlace((Place)card);
        if(card.isHelper()) return new GUIHelper((Helper)card);
        if(card.isCharacter()) return new GUICharacter((Character)card);

        return null;
    }
}
