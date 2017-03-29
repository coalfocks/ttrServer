package com.example.tyudy.ticket2rideclient.common.decks;

import com.example.tyudy.ticket2rideclient.common.ColorENUM;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCard;
import com.example.tyudy.ticket2rideclient.common.cards.iCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zacheaton on 3/7/17.
 */
public class TrainCardDeck implements iDeck, Serializable
{
    private List<iCard> cards;
    private TTRGame currentGame;

    public TrainCardDeck()
    {
        cards = new ArrayList<iCard>();
    }

    public void setCurrentGame(TTRGame game)
    {
        currentGame = game;
    }

    public void initCards()
    {
        for (ColorENUM c : ColorENUM.values())
        {
            for(int i = 0; i < 12; i++){
                TrainCard newCard = new TrainCard(c);
                this.addCard(newCard);
            }
        }

        // There are 14 locamotives in the train deck
        for (int x = 0; x < 14; ++x)
            this.addCard(new TrainCard(ColorENUM.WILD));

        this.shuffle();
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    };

    public void addCard(iCard card){
        this.cards.add(card);
    }

    public List<iCard> getDeck() { return cards; }

    public iCard getCard()
    {
        if(cards.size() > 0) {
            iCard myCard = cards.get(cards.size() - 1);
            cards.remove(cards.size() - 1);
            return myCard;
        }
        else if (currentGame != null)
        {
            TrainCardDeck newDeck = currentGame.getTrainDiscardDeck();
            newDeck.shuffle();
            cards = newDeck.getDeck();

            return getCard();
        }
        else
        {
            return null;
        }
    }
}
