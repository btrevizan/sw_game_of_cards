import java.util.Observer;
import java.util.Observable;
import java.util.LinkedList;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.text.*;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

public abstract class GUICard extends GridPane implements Observer{

    private Label name;
    private Label type;
    private Label description;
    private ImageView image;

    private ObservableList<Integer> properties;
    private LinkedList<String> propertiesName;

    public GUICard(Card card){
        this.name = new Label();
        this.type = new Label();
        this.description = new Label();
        this.image = new ImageView();

        this.properties = card.getProperties();

        this.propertiesName = new LinkedList<>();
        this.propertiesName.add("Hab.");
        this.propertiesName.add("Poder");
        this.propertiesName.add("Veloc.");
        this.propertiesName.add("Proteção");

        this.setName(card.getName());
        this.setType(card.getType());
        this.setImage(card.getName());
        this.setDescription(card.getDescription());
        this.setGrid();
        this.setProperties();

        card.addObserver(this);
    }

    private void setGrid(){
        int pad = Default.gridPadding;
        this.setPadding(new Insets(pad, pad, pad, pad));

        this.setAlignment(Pos.TOP_CENTER);

        this.setHgap(Default.gridGap);
        this.setVgap(Default.gridGap);

        this.setWidth(Default.cardWidth);
        this.setStyle("-fx-background-color: " + Default.cardBGColor);

        for(int i = 0; i < Default.cardColumns; i++)
            this.getColumnConstraints().add(new ColumnConstraints(Default.cardColumnWidth));

        this.add(this.name, 0, 1, 3, 1);
        this.add(this.type, 3, 1, 1, 1);
        this.add(this.image, 0, 2, 4, 1);
        this.add(this.description, 0, 3, 4, 1);
    }

    private void setName(String name){
        this.name.setText(name);
        this.name.setWrapText(true);
        this.name.setFont(Font.font(Default.font, FontWeight.BOLD, 12));
    }

    private void setType(String type){
        this.type.setText(type);
        this.type.setWrapText(true);
        this.type.setFont(Font.font(Default.font, FontWeight.LIGHT, 10));
    }

    private void setImage(String name){
        this.image.setImage(new Image("file:assets/imgs/" + name + ".jpeg"));
        this.image.setFitWidth(Default.cardWidth);
        this.image.setPreserveRatio(true);
    }

    private void setDescription(String description){
        this.description.setText(description);
        this.description.setWrapText(true);
        this.description.setFont(Font.font(Default.font, FontWeight.LIGHT, 8));
    }

    private void setProperties(){
        this.getChildren().remove(4, this.getChildren().size());

        int line = 4, k = 0;
        for(int i = 0; i < this.properties.size(); i++){
            Integer property = this.properties.get(i);

            if(property > 0){
                Label name = new Label(this.propertiesName.get(i));
                Label value = new Label(""+property);

                this.add(this.box(name, value), (k % 2) * 2, line, 2, 1);

                if(k % 2 != 0) line++;
                k++;
            }
        }
    }

    public HBox box(Label name, Label value){
        name.setWrapText(true);
        name.setAlignment(Pos.BOTTOM_RIGHT);
        name.setFont(Font.font(Default.font, FontWeight.LIGHT, 10));

        value.setWrapText(true);
        value.setAlignment(Pos.BOTTOM_LEFT);
        value.setFont(Font.font(Default.font, FontWeight.BOLD, 12));

        HBox box = new HBox(Default.gridGap);
        box.getChildren().addAll(name, value);
        box.setAlignment(Pos.BOTTOM_LEFT);

        return box;
    }

    @Override
    public void update(Observable o, Object arg){
        if(o instanceof Card){
            Card card = (Card) o;
            this.properties = card.getProperties();
            this.setProperties();
        }
    }
}
