import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import javafx.event.*;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class GUINewGame extends GridPane{

    private TextField player1;
    private TextField player2;
    private Label message;

    public GUINewGame(NewGameStage newGameStage){
        this.player1 = new TextField();
        this.player2 = new TextField();
        this.message = new Label();

        this.setGrid(newGameStage);
    }

    private void setGrid(NewGameStage newGameStage){
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(50, 50, 50, 50));

        Text title = new Text(Default.gameMainTitle);
        Text subtitle = new Text(Default.gameSubTitle);

        title.setFont(Font.font(Default.font, FontWeight.BOLD, 32));
        subtitle.setFont(Font.font(Default.font, FontWeight.LIGHT, 16));

        Label player1Label = new Label("Jogador 1");
        Label player2Label = new Label("Jogador 2");

        Button newGameBtn = new Button("Novo Jogo");
        newGameBtn.setCursor(Cursor.HAND);

        newGameBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                String name1 = player1.getText();
                String name2 = player2.getText();

                if(name1.length() == 0 || name2.length() == 0){
                    setMessage("Todos os campos são obrigatórios");
                    return;
                }

                ArenaStage arena = new ArenaStage(name1, name2);
                newGameStage.close();
                arena.show();
            }
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(this.message, newGameBtn);

        HBox msg = new HBox(10);
        msg.setAlignment(Pos.BOTTOM_LEFT);
        msg.getChildren().addAll(title, subtitle);

        this.add(msg, 0, 0, 2, 1);

        this.add(player1Label, 0, 1);
        this.add(player2Label, 0, 2);

        this.add(this.player1, 1, 1);
        this.add(this.player2, 1, 2);

        this.add(hbox, 0, 3, 2, 1);

    }

    private void setMessage(String message){
        this.message.setText(message);
        this.message.setWrapText(true);
        this.message.setFont(Font.font(Default.font, FontWeight.LIGHT, 11));
    }

}
