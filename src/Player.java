import java.util.Observable;
import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Player extends Observable{

    private String name;
    private long points;
    private LinkedList<Card> hand;

    public Player(String name){
        this.name = name;
        this.reboot();
    }

    public String getName(){
        return this.name;
    }

    public long getPoints(){
        return this.points;
    }

    public ObservableList<Card> getHand(){
        return FXCollections.observableList(this.hand);
    }

    public int handSize(){
        return this.hand.size();
    }

    public void addPoints(long points){
        this.points += points;
        this.notifyObservers();
    }

    public void reboot(){
        this.points = 0;
        this.hand = new LinkedList<>();
        this.notifyObservers();
    }

    public boolean play(int i){
        if(this.hand.size() == 0) return false;

        if(Arena.getInstance().setCard(this.hand.get(i))){
            this.hand.remove(i);
            this.notifyObservers();
            return true;
        }

        return false;
    }

    public boolean draw(Card card){
        if(this.handSize() == Default.handSize) return false;
        if(card == null) return false;

        if(card.isCharacter()){
            Character character = (Character) card;
            if(character.getLife() == 0) return false;
        }

        this.hand.add(card);
        this.notifyObservers();
        return true;
    }

    public boolean draw(){
        return this.draw(DeckOfCards.getInstance().next());
    }

    public boolean hasCharacter(){
        for(Card card : this.hand)
            if(card.isCharacter()) return true;

        return false;
    }

    @Override
    public void notifyObservers(){
        this.setChanged();
        super.notifyObservers();
    }
}
