import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

import java.util.Observable;
import java.util.LinkedList;
import java.util.Collections;

public class DeckOfCards extends Observable{

    private static DeckOfCards instance = null;
    private LinkedList<Card> cards;

    private DeckOfCards(){
        this.cards = new LinkedList<>();

        Path filepath = Paths.get("assets/cards.txt");
        try(BufferedReader file = Files.newBufferedReader(filepath, Charset.defaultCharset())){

            CardSelector cardSelector = CardSelector.getInstance();
            String line;

            while((line = file.readLine()) != null){
                String[] info = line.split("%");

                for(int i = 0; i < Integer.parseInt(info[0]); i++)
                    this.cards.add(cardSelector.createCard(info));
            }

            this.shuffle();
        } catch(IOException e) {
            System.err.format("Erro de I/O: %s%n", e);
        }
    }

    public static DeckOfCards newInstance(){
        instance = new DeckOfCards();
        return instance;
    }

    public static DeckOfCards getInstance(){
        if(instance == null) return newInstance();
        return instance;
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    public int size(){
        return this.cards.size();
    }

    public Card next(){
        if(this.size() == 0) return null;
        this.notifyObservers();
        return this.cards.remove(0);
    }

    @Override
    public void notifyObservers(){
        this.setChanged();
        super.notifyObservers();
    }
}
