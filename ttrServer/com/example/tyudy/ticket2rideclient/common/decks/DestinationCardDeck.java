package com.example.tyudy.ticket2rideclient.common.decks;

import com.example.tyudy.ticket2rideclient.common.cards.iCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zacheaton on 3/7/17.
 */
public class DestinationCardDeck implements iDeck{

    //initialize a deck TODO: figure out wtf to do here.
    public DestinationCardDeck() {

    }
        public void shuffle(){
            Collections.shuffle(this.cards);
        };

    public  void addCard(iCard card){
        this.cards.add(card);
    }
    public  iCard getCard(){
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