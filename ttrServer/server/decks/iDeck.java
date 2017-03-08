package server.decks;

import com.example.tyudy.ticket2rideclient.common.cards.iCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zacheaton on 3/7/17.
 */
public interface iDeck {
    List<iCard> cards = new ArrayList<iCard>();

    public default void shuffle(){
        Collections.shuffle(this.cards);
    };

    public default void addCard(iCard card){
        this.cards.add(card);
    }
    public default iCard getCard(){
        if(cards.size()>0) {
            iCard myCard = cards.get(cards.size());
            cards.remove(cards.size());
            return myCard;
        }
        else{
            return null;
        }
    }
}
