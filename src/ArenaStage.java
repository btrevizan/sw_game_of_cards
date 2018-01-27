import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArenaStage extends Stage{

    public ArenaStage(String player1, String player2){
        Arena arena = Arena.newInstance(player1, player2);
        GUIArena guiArena = new GUIArena(arena, this);
        Scene scene = new Scene(guiArena);

        this.setTitle(Default.gameTitle);
        this.setScene(scene);
    }

}
