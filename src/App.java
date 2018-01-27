import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        primaryStage = new NewGameStage();
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
