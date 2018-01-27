import java.util.Observable;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Card extends Observable implements ICard{

    private String name;
    private String description;
    private LinkedList<Integer> properties;

    public Card(String name, String description, Integer ability, Integer power, Integer speed, Integer protection){
        this.name = name;
        this.description = description;
        this.properties = new LinkedList<>();

        this.properties.add(ability);
        this.properties.add(power);
        this.properties.add(speed);
        this.properties.add(protection);
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Integer getAbility(){
        return this.properties.get(0);
    }

    public Integer getPower(){
        return this.properties.get(1);
    }

    public Integer getSpeed(){
        return this.properties.get(2);
    }

    public Integer getProtection(){
        return this.properties.get(3);
    }

    public ObservableList<Integer> getProperties(){
        return FXCollections.observableList(this.properties);
    }

    public String getType(){
        if(this instanceof Master) return "Jedi";
        if(this instanceof Knight) return "Jedi";
        if(this instanceof Creature) return "Criatura";
        if(this instanceof Fighter) return "Soldado";
        if(this instanceof Weapon) return "Arma";
        if(this instanceof Vehicle) return "Veículo";
        if(this instanceof Place) return "Lugar";

        return "";
    }

    public void setAbility(Integer ability){
        this.properties.set(0, ability);
        this.notifyObservers();
    }

    public void setPower(Integer power){
        this.properties.set(1, power);
        this.notifyObservers();
    }

    public void setSpeed(Integer speed){
        this.properties.set(2, speed);
        this.notifyObservers();
    }

    public void setProtection(Integer protection){
        this.properties.set(3, protection);
        this.notifyObservers();
    }

    public boolean isCharacter(){
        return !this.isHelper() && !this.isPlace();
    }

    public boolean isHelper(){
        return this instanceof Weapon || this instanceof Vehicle;
    }

    public boolean isPlace(){
        return this instanceof Place;
    }

    public void reset(){
        this.setProtection(0);
        this.notifyObservers();
    }

    public String toString(){
        return this.getName();
    }

    @Override
    public void notifyObservers(){
        this.setChanged();
        super.notifyObservers();
    }
}
