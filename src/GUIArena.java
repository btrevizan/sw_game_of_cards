import java.util.Observer;
import java.util.Observable;
import java.util.LinkedList;
import javafx.collections.ObservableList;

import javafx.stage.Stage;
import javafx.event.*;

import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class GUIArena extends GridPane implements Observer{

    private GUIAlert message;
    private TabPane hands;
    private VBox scoreboard;
    private LinkedList<Button> buttons;

    private ObservableList<Card> cards;
    private GridPane battleField;

    public GUIArena(Arena arena, Stage stage){
        this.message = GUIAlert.getInstance();
        this.hands = new TabPane();
        this.battleField = new GridPane();
        this.cards = arena.getCards();
        this.scoreboard = new VBox(10);

        this.setMenu(stage);
        this.setHands();
        this.setBattleField();
        this.setGrid();

        arena.addObserver(this);
        arena.getTurnPlayer().addObserver(this);
        arena.getOtherPlayer().addObserver(this);
    }

    private void setGrid(){
        int pad = Default.gridPadding / 2;
        this.setPadding(new Insets(pad, pad, pad, pad));

        this.setAlignment(Pos.CENTER);

        this.setHgap(Default.gridGap);
        this.setVgap(Default.gridGap);

        for(int i = 0; i < Default.handSize; i++)
            this.getColumnConstraints().add(new ColumnConstraints(Default.arenaColumnWidth));

        this.setScoreboard();
        this.setCards();
        this.setDeck();

        this.add(this.battleField, 1, 0, 3, 1);
        this.add(this.hands, 0, 1, Default.handSize, 1);
    }

    private void setMessage(String message){
        this.message.setText(message);
    }

    private void setDeck(){
        Weapon helper = new Weapon("Star Wars", "Clique aqui para tirar uma carta do baralho.", 0);
        GUIHelper deck = new GUIHelper(helper);
        deck.setCursor(Cursor.HAND);

        deck.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(DeckOfCards.getInstance().size() == 0)
                    setMessage("Baralho vazio");
                else if(!Arena.getInstance().getTurnPlayer().draw())
                    setMessage("Oops!!! Você não pode retirar uma carta do baralho.");
            }
        });

        this.add(deck, 0, 0);
    }

    private void setScoreboard(){
        this.scoreboard.setAlignment(Pos.BOTTOM_RIGHT);
        this.scoreboard.setPadding(new Insets(5, 30, 5, 0));

        ObservableList<Player> players = Arena.getInstance().getPlayers();
        this.scoreboard.getChildren().remove(0, this.scoreboard.getChildren().size());

        for(Player player : players){
            Label name = new Label(player.getName());
            Label score = new Label(""+player.getPoints());

            name.setWrapText(true);
            name.setFont(Font.font(Default.font, FontWeight.LIGHT, 12));

            score.setWrapText(true);
            score.setFont(Font.font(Default.font, FontWeight.BOLD, 16));

            HBox hbox = new HBox(Default.gridGap);
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            hbox.getChildren().addAll(name, score);

            this.scoreboard.getChildren().add(hbox);
        }
    }

    private void setMenu(Stage stage){
        this.buttons = new LinkedList<>();
        String[] buttonsName = {"Finalizar Jogada", "Embaralhar", "Reiniciar Jogo", "Novo Jogo", "Sair", "Mostrar Cartas"};

        for(int i = 0; i < buttonsName.length; i++)
            buttons.add(new Button(buttonsName[i]));

        VBox menu = new VBox(10);
        for(Button button : buttons){
            this.setPrefMenuButton(button);
            menu.getChildren().add(button);
        }

        menu.getChildren().add(this.message);
        menu.getChildren().add(this.scoreboard);

        this.add(menu, 4, 0);

        this.buttons.get(0).setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Arena arena = Arena.getInstance();
                if(arena.endTurn())
                    selectTab(arena.getTurn());
                else
                    setMessage("Oops!!! Jogada inválida.");
            }
        });

        this.buttons.get(1).setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                DeckOfCards.getInstance().shuffle();
                setMessage("Baralho embaralhado!");
            }
        });

        this.buttons.get(2).setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Arena.getInstance().reboot();

                buttons.get(0).setDisable(false);
                buttons.get(1).setDisable(false);
                buttons.get(5).setDisable(false);

                selectTab(0);
                setMessage("Jogo reiniciado!");
            }
        });

        this.buttons.get(3).setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                NewGameStage newGame = new NewGameStage();
                stage.close();
                newGame.show();
            }
        });

        this.buttons.get(4).setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                System.exit(0);
            }
        });

        this.buttons.get(5).setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                hands.setVisible(true);
                buttons.get(5).setDisable(true);
            }
        });
    }

    private void setHands(){
        ObservableList<Player> players = Arena.getInstance().getPlayers();

        for(Player player : players){
            Tab hand = new Tab(player.getName());
            hand.setClosable(false);
            hand.setContent(new GUIHand(player));
            this.hands.getTabs().add(hand);
        }

        this.selectTab(0);
    }

    private void setBattleField(){
        this.battleField.setAlignment(Pos.CENTER_LEFT);
        this.battleField.setHgap(Default.gridGap);
        this.battleField.setVgap(Default.gridGap);
    }

    private void setCards(){
        GUICardSelector cardSelector = GUICardSelector.getInstance();
        this.battleField.getChildren().remove(0, this.battleField.getChildren().size());

        for(int i = 0; i < this.cards.size(); i++){
            Card card = this.cards.get(i);
            if(card != null){
                GUICard guiCard = cardSelector.createGUICard(card);
                guiCard.setCursor(Cursor.HAND);
                this.battleField.add(guiCard, i, 0);
            } else {
                this.battleField.add(new GridPane(), i, 0);
            }
        }
    }

    private void setPrefMenuButton(Button button){
        button.setPrefWidth(Default.arenaWidth / Default.handSize);
        button.setCursor(Cursor.HAND);
    }

    private void selectTab(int a){
        int b = 0;
        if(a == 0) b = 1;

        SingleSelectionModel tabs = this.hands.getSelectionModel();
        ObservableList<Tab> players = this.hands.getTabs();

        players.get(a).setDisable(false);
        players.get(b).setDisable(true);

        tabs.select(a);
        this.hands.setVisible(false);
        this.buttons.get(5).setDisable(false);

        if(!Arena.getInstance().isFinished())
            setMessage("Sua vez, " + players.get(a).getText());
    }

    @Override
    public void update(Observable o, Object arg){
        if(Arena.getInstance().isFinished()){
            Label win = new Label();
            Player winner = Arena.getInstance().getWinner();

            if(winner == null) setMessage("Vish, empatou.");
            else setMessage("Parabéns, " + winner.getName() + "! Você ganhou.");

            this.buttons.get(0).setDisable(true);
            this.buttons.get(1).setDisable(true);
            this.buttons.get(5).setDisable(true);

            return;
        }

        if(o instanceof Arena){
            Arena arena = (Arena) o;
            this.cards = arena.getCards();
            this.setCards();
        } else if(o instanceof Player){
            this.setScoreboard();
        }
    }
}
