import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewGameStage extends Stage{

    public NewGameStage(){
        GUINewGame newGame = new GUINewGame(this);
        Scene scene = new Scene(newGame);

        this.setTitle(Default.gameTitle + " - Novo Jogo");
        this.setScene(scene);
    }

}
