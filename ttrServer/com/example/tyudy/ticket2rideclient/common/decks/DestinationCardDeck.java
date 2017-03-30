package com.example.tyudy.ticket2rideclient.common.decks;

import com.example.tyudy.ticket2rideclient.common.Destination;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cards.iCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zacheaton on 3/7/17.
 */
public class DestinationCardDeck implements iDeck, Serializable {
    private List<iCard> cards;
    private TTRGame currentGame;

    public DestinationCardDeck() {
        cards = new ArrayList<>();
    }

    public void initCards(){
        cards.add(new DestinationCard("Los Angeles", "New York", 21));
        cards.add(new DestinationCard("Duluth", "Houston", 8));
        cards.add(new DestinationCard("Sault St Marie", "Nashville", 8));
        cards.add(new DestinationCard("New York", "Atlanta", 6));
        cards.add(new DestinationCard("Portland", "Nashville", 17));
        cards.add(new DestinationCard("Vancouver", "Montreal", 20));
        cards.add(new DestinationCard("Duluth", "El Paso", 10));
        cards.add(new DestinationCard("Toronto", "Miami", 10));
        cards.add(new DestinationCard("Portland", "Phoenix", 11));
        cards.add(new DestinationCard("Dallas", "New York", 11));
        cards.add(new DestinationCard("Calgary", "Salt Lake", 7));
        cards.add(new DestinationCard("Calgary", "Phoenix", 13));
        cards.add(new DestinationCard("Los Angeles", "Miami", 20));
        cards.add(new DestinationCard("Winnipeg", "Little Rock", 11));
        cards.add(new DestinationCard("San Francisco", "Atlanta", 17));
        cards.add(new DestinationCard("Kansas City", "Houston", 5));
        cards.add(new DestinationCard("Los Angeles", "Chicago", 16));
        cards.add(new DestinationCard("Denver", "Pittsburgh", 11));
        cards.add(new DestinationCard("Chicago", "Santa Fe", 9));
        cards.add(new DestinationCard("Vancouver", "Santa Fe", 13));
        cards.add(new DestinationCard("Boston", "Miami", 12));
        cards.add(new DestinationCard("Chicago", "New Orleans", 7));
        cards.add(new DestinationCard("Montreal", "Atlanta", 9));
        cards.add(new DestinationCard("Seattle", "New York", 22));
        cards.add(new DestinationCard("Denver", "El Paso", 4));
        cards.add(new DestinationCard("Helena", "Los Angeles", 8));
        cards.add(new DestinationCard("Winnipeg", "Houston", 12));
        cards.add(new DestinationCard("Montreal", "New Orleans", 13));
        cards.add(new DestinationCard("Sault St Marie", "Oklahoma City", 9));
        cards.add(new DestinationCard("Seattle", "Los Angeles", 9));

        shuffle();


        /*
        Denver to El Paso (4)
        Kansas City to Houston (5)
        New York to Atlanta (6)
        Chicago to New Orleans (7), Calgary to Salt Lake City (7)
        Helena to Los Angeles (8), Duluth to Houston (8), Sault Ste Marie to Nashville (8)
        Montreal to Atlanta (9), Sault Ste. Marie to Oklahoma City (9), Seattle to Los Angeles (9), Chicago to Santa Fe (9)
        Duluth to El Paso (10), Toronto to Miami (10)
        Portland to Phoenix(11), Dallas to New York City (11), Denver to Pittsburgh (11), Winnipeg to Little Rock (11)
        Winnipeg to Houston (12), Boston to Miami (12)
        Vancouver to Santa Fe (13), Calgary to Phoenix(13), Montreal to New Orleans (13)
        Los Angeles to Chicago (16)
        San Francisco to Atlanta (17), Portland to Nashville (17)
        Vancouver to Montréal (20), Los Angeles to Miami (20)
        Los Angeles to New York City (21)
        Seattle to New York (22)*/
    }
    public void shuffle(){
            Collections.shuffle(this.cards);
        };

    public List<iCard> getDeck() { return cards; }

    public void setCurrentGame(TTRGame game) { currentGame = game; }

    public void addCard(iCard card){
        this.cards.add(card);
    }

    public iCard getCard(){
        if(cards.size() > 0) {
            iCard myCard = cards.get(cards.size() - 1);
            cards.remove(cards.size() - 1);
            return myCard;
        }
        else if (currentGame != null)
        {
            DestinationCardDeck newDeck = currentGame.getDestDiscardDeck();
            newDeck.shuffle();
            cards = newDeck.getDeck();

            currentGame.clearDestDiscardDeck();

            return getCard();
        }
        else
        {
            return null;
        }
    }

}
