package unsw.loopmania.Loaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Cards.*;

public class CardLoader{

    /**
     * Creating and returning a random card object (Factory pattern)
     * @param cardEntitiesSize The total number of cards on the on the screen
     * @return A random card object
     */
    public static Card loadRandomCard(int cardEntitiesSize){
        /* Initialize a list of cards */
        List<Card> cards = new ArrayList<>();
        /* Now add all types of card into the list */
        /* TODO: Destroy all non used objects? */
        cards.add(new VampireCastleCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        cards.add(new ZombiePitCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        cards.add(new TowerCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        cards.add(new VillageCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        cards.add(new BarracksCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        cards.add(new TrapCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        cards.add(new CampfireCard(new SimpleIntegerProperty(cardEntitiesSize), new SimpleIntegerProperty(0)));
        /* Now randomly choose a card */
        return cards.get(new Random().nextInt(cards.size()));
    }

}
