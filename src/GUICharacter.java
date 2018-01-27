import java.util.Observable;
import javafx.collections.ObservableList;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class GUICharacter extends GUICard{

    private ProgressBar life;

    public GUICharacter(Character card){
        super(card);

        this.life = new ProgressBar();

        this.setLife(card.getLife());
        this.setPlace(card.getPlace());
    }

    private void setLife(double life){
        this.life.setProgress(life / 100);
        this.life.setPrefWidth(Default.cardWidth);
        this.add(this.life, 0, 0, 4, 1);
    }

    private void setPlace(String place){
        HBox box = this.box(new Label("Lugar"), new Label(place));
        this.add(box, 0, 6, 4, 1);
    }

    @Override
    public void update(Observable o, Object arg){
        super.update(o, arg);
        if(o instanceof Character){
            Character character = (Character) o;
            setLife(character.getLife());
            setPlace(character.getPlace());
        }
    }
}
