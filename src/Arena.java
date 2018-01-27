import java.util.Observable;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Arena extends Observable{

    private static Arena instance = null;

    private LinkedList<Player> players;
    private LinkedList<Card> cards;

    private DeckOfCards deck;
    private int plays;
    private boolean nextTurn;

    private Arena(String name1, String name2){
        Player p1 = new Player(name1);
        Player p2 = new Player(name2);

        this.players = new LinkedList<>();
        this.players.add(p1);
        this.players.add(p2);

        this.reboot();
    }

    public static Arena newInstance(String name1, String name2){
        instance = new Arena(name1, name2);
        return instance;
    }

    public static Arena getInstance(String name1, String name2){
        if(instance == null) return newInstance(name1, name2);
        return instance;
    }

    public static Arena getInstance(){
        return instance;
    }

    public ObservableList<Player> getPlayers(){
        return FXCollections.observableList(this.players);
    }

    public ObservableList<Card> getCards(){
        return FXCollections.observableList(this.cards);
    }

    public int getTurn(){
        return this.plays % 2;
    }

    public int getOtherTurn(){
        return (this.plays + 1) % 2;
    }

    public Player getTurnPlayer(){
        return this.players.get(this.getTurn());
    }

    public Player getOtherPlayer(){
        return this.players.get(this.getOtherTurn());
    }

    public Player getWinner(){
        Player p1 = this.players.get(0);
        Player p2 = this.players.get(1);

        if(p1.getPoints() > p2.getPoints()) return p1;
        if(p2.getPoints() > p1.getPoints()) return p2;

        return null;
    }

    private Character getTurnCharacter(){
        return (Character) this.cards.get(this.getTurn());
    }

    private Character getOtherCharacter(){
        return (Character) this.cards.get(this.getOtherTurn());
    }

    public void nextTurn(){
        if(this.nextTurn) this.plays++;
        this.nextTurn = !this.nextTurn;
    }

    public boolean setCard(Card card){
        boolean response = true;

        if(card.isHelper()) {
            if(this.getTurnCharacter() != null)
                return this.getTurnCharacter().setHelper((Helper) card);
            else
                return false;
        }

        if(card.isCharacter()) {
            this.unsetCard();
            this.cards.set(this.getTurn(), card);
        } else if(card.isPlace()) {
            response = this.setPlace((Place) card);
        }

        this.setCardProtection();
        this.notifyObservers();
        return response;
    }

    private void unsetCard(){
        this.getTurnPlayer().draw(this.getTurnCharacter());
        this.unsetCardProtection();
        this.cards.set(this.getTurn(), null);
        this.notifyObservers();
    }

    private boolean setPlace(Place card){
        if(this.cards.get(2) != null) return false;
        this.cards.set(2, card);
        return true;
    }

    private void unsetPlace(){
        this.cards.set(2, null);
        this.notifyObservers();
    }

    private void setCardProtection(){
        Place place = (Place) this.cards.get(2);
        if(place == null) return;

        for(int i = 0; i < this.cards.size()-1; i++){
            Character c = (Character) this.cards.get(i);
            if(c != null && place.isPlace(c)) c.setProtection(place.getProtection());
        }
    }

    private void unsetCardProtection(){
        if(this.getTurnCharacter() == null) return;
        this.getTurnCharacter().setProtection(0);
    }

    private void reset(){
        for(Card card : this.cards)
            if(card != null) card.reset();

        this.unsetPlace();
    }

    private boolean fight(){
        if(this.cards.get(0) == null) return true;
        if(this.cards.get(1) == null) return true;

        if(this.getTurnCharacter().getLife() == 0) return false;
        if(this.getOtherCharacter().getLife() == 0) return true;

        LinkedList<Double> points = this.getTurnCharacter().attack(this.getOtherCharacter());

        this.getTurnPlayer().addPoints(Math.round(points.get(0)));
        this.getOtherPlayer().addPoints(Math.round(points.get(1)));

        this.reset();
        return true;
    }

    public boolean endTurn(){
        if(!this.fight()) return false;
        this.nextTurn();
        return true;
    }

    public void reboot(){
        this.plays = 0;
        this.nextTurn = true;

        this.cards = new LinkedList<>();
        for(int i = 0; i < 3; i++)
            this.cards.add(null);

        this.setHands();
        this.notifyObservers();
    }

    private void setHands(){
        this.deck = DeckOfCards.newInstance();

        for(Player player : this.players)
            player.reboot();

        for(int i = 0; i < Default.handSize; i++){
            this.players.get(0).draw(this.deck.next());
            this.players.get(1).draw(this.deck.next());
        }

        boolean hasChar = true;
        for(Player player : this.players)
            if(!player.hasCharacter()) hasChar = false;

        if(!hasChar) this.setHands();
    }

    public boolean isFinished(){
        if(this.deck.size() == 0){
            for(int i = 0; i < this.players.size(); i++){
                if(!this.players.get(i).hasCharacter()){
                    Character character = (Character) this.cards.get(i);
                    if(character == null) return true;
                    if(character.getLife() == 0) return true;
                }
            }
        } else {
            for(int i = 0; i < this.players.size(); i++){
                if(this.players.get(i).handSize() == Default.handSize){
                    if(!this.players.get(i).hasCharacter()){
                        Character character = (Character) this.cards.get(i);
                        if(character == null) return true;
                        if(character.getLife() == 0) return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void notifyObservers(){
        this.setChanged();
        super.notifyObservers();
    }
}
