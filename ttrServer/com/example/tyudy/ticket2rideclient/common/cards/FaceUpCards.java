package com.example.tyudy.ticket2rideclient.common.cards;

import com.example.tyudy.ticket2rideclient.common.ColorENUM;

import java.io.Serializable;

/**
 * Created by colefox on 3/29/17.
 */

public class FaceUpCards implements Serializable {
    private TrainCard card1;
    private TrainCard card2;
    private TrainCard card3;
    private TrainCard card4;
    private TrainCard card5;

    public TrainCard getCard1()
    {
        return card1;
    }

    public void setCard1(TrainCard card1)
    {
        this.card1 = card1;
    }

    public TrainCard getCard2()
    {
        return card2;
    }

    public void setCard2(TrainCard card2)
    {
        this.card2 = card2;
    }

    public TrainCard getCard3()
    {
        return card3;
    }

    public void setCard3(TrainCard card3)
    {
        this.card3 = card3;
    }

    public TrainCard getCard4()
    {
        return card4;
    }

    public void setCard4(TrainCard card4)
    {
        this.card4 = card4;
    }

    public TrainCard getCard5()
    {
        return card5;
    }

    public void setCard5(TrainCard card5)
    {
        this.card5 = card5;
    }
}
