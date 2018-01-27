import java.util.Observer;
import java.util.Observable;
import java.util.LinkedList;
import javafx.collections.ObservableList;

import javafx.event.*;

import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;

import javafx.geometry.Pos;

public class GUIHand extends GridPane implements Observer{

    private Player player;
    private GUIAlert message;
    private ObservableList<Card> cards;

    public GUIHand(Player player){
        this.player = player;
        this.message = GUIAlert.getInstance();
        this.cards = player.getHand();

        this.setGrid();
        this.setCards();

        player.addObserver(this);
    }

    private void setGrid(){
        this.setAlignment(Pos.CENTER);

        this.setHgap(Default.gridGap);
        this.setVgap(Default.gridGap);

        for(int i = 0; i < Default.handSize; i++)
            this.getColumnConstraints().add(new ColumnConstraints(Default.arenaColumnWidth));
    }

    private void setMessage(String message){
        this.message.setText(message);
    }

    private void setCards(){
        GUICardSelector cardSelector = GUICardSelector.getInstance();
        this.getChildren().remove(0, this.getChildren().size());

        for(int i = 0; i < this.cards.size(); i++){
            Card card = this.cards.get(i);
            if(card != null){
                GUICard guiCard = cardSelector.createGUICard(card);
                guiCard.setCursor(Cursor.HAND);
                this.add(guiCard, i, 0);

                final int k = i;
                guiCard.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    public void handle(MouseEvent e){
                        if(!player.play(k))
                            setMessage("Oops!!! Jogada inválida.");
                    }
                });
            } else {
                this.add(new GridPane(), i, 0);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg){
        if(o instanceof Player){
            Player player = (Player) o;
            this.cards = player.getHand();
            this.setCards();
        }
    }
}
