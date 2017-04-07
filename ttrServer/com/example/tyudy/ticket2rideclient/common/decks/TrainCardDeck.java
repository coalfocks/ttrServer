package com.example.tyudy.ticket2rideclient.common.decks;

import com.example.tyudy.ticket2rideclient.common.ColorENUM;
import com.example.tyudy.ticket2rideclient.common.cards.FaceUpCards;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCardCollection;
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

    private List<iCard> cards = new ArrayList<iCard>();
    private FaceUpCards faceUpCards = new FaceUpCards();

    //initialize the deck
    public TrainCardDeck(){
        for (ColorENUM c : ColorENUM.values()) {
            if (c == ColorENUM.COLORLESS) {
                continue;
            }
            for(int i = 0; i < 12; i++){
                TrainCardCollection newCard = new TrainCardCollection(c);
                this.addCard(newCard);
            }
        }
        TrainCardCollection newWild = new TrainCardCollection(ColorENUM.WILD);
        this.addCard(newWild);
        this.addCard(newWild);
        this.shuffle();
        faceUpCards.setCard1((TrainCardCollection) this.getCard());
        faceUpCards.setCard2((TrainCardCollection) this.getCard());
        faceUpCards.setCard3((TrainCardCollection) this.getCard());
        faceUpCards.setCard4((TrainCardCollection) this.getCard());
        faceUpCards.setCard5((TrainCardCollection) this.getCard());
    }
    public void shuffle(){
        Collections.shuffle(this.cards);
    };

    public  void addCard(iCard card){
        this.cards.add(card);
    }
    public  iCard getCard(){
        if(cards.size()>0) {
            iCard myCard = cards.get(cards.size() - 1);
            cards.remove(cards.size() - 1);
            return myCard;
        } else {
            return null;
        }
    }

    public FaceUpCards getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(FaceUpCards faceUpCards) {
        this.faceUpCards = faceUpCards;
    }

    public void swapFaceUpCard(int index) {
        switch (index) {
            case 1:
                faceUpCards.setCard1((TrainCardCollection) this.getCard());
                break;
            case 2:
                faceUpCards.setCard2((TrainCardCollection) this.getCard());
                break;
            case 3:
                faceUpCards.setCard3((TrainCardCollection) this.getCard());
                break;
            case 4:
                faceUpCards.setCard4((TrainCardCollection) this.getCard());
                break;
            case 5:
                faceUpCards.setCard5((TrainCardCollection) this.getCard());
                break;
        }
    }
}
