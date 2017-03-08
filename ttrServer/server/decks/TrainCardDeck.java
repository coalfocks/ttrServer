package server.decks;

import com.example.tyudy.ticket2rideclient.common.Color;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCard;

/**
 * Created by zacheaton on 3/7/17.
 */
public class TrainCardDeck implements iDeck {

    //initialize the deck
    public TrainCardDeck(){
        for (Color c : Color.values()){
            for(int i = 0; i < 12; i++){
                TrainCard newCard = new TrainCard(c);
                this.addCard(newCard);
            }
        }
        TrainCard newWild = new TrainCard(Color.WILD);
        this.addCard(newWild);
        this.addCard(newWild);
        this.shuffle();
    }
}
