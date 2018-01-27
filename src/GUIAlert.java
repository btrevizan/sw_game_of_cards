import javafx.scene.control.*;

public class GUIAlert extends Label{

    private static GUIAlert instance = null;

    private GUIAlert(){
        this.setWrapText(true);
    }

    public static GUIAlert getInstance(){
        if(instance == null) instance = new GUIAlert();
        return instance;
    }
}
