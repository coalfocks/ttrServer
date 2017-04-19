package com.example.tyudy.ticket2rideclient.common.cards;

import com.example.tyudy.ticket2rideclient.common.ColorENUM;

import java.io.Serializable;

/**
 * Created by colefox on 3/29/17.
 */

public class FaceUpCards implements Serializable {
    private TrainCardCollection card1;
    private TrainCardCollection card2;
    private TrainCardCollection card3;
    private TrainCardCollection card4;
    private TrainCardCollection card5;

    public TrainCardCollection getCard1()
    {
        return card1;
    }

    public void setCard1(TrainCardCollection card1)
    {
        this.card1 = card1;
    }

    public TrainCardCollection getCard2()
    {
        return card2;
    }

    public void setCard2(TrainCardCollection card2)
    {
        this.card2 = card2;
    }

    public TrainCardCollection getCard3()
    {
        return card3;
    }

    public void setCard3(TrainCardCollection card3)
    {
        this.card3 = card3;
    }

    public TrainCardCollection getCard4()
    {
        return card4;
    }

    public void setCard4(TrainCardCollection card4)
    {
        this.card4 = card4;
    }

    public TrainCardCollection getCard5()
    {
        return card5;
    }

    public void setCard5(TrainCardCollection card5)
    {
        this.card5 = card5;
    }

    public boolean wildsOkay () {
        int wilds = 0;
        if (card1.getColor() == ColorENUM.WILD) {
            wilds++;
        }
        if (card2.getColor() == ColorENUM.WILD) {
            wilds++;
        }
        if (card3.getColor() == ColorENUM.WILD) {
            wilds++;
        }
        if (card4.getColor() == ColorENUM.WILD) {
            wilds++;
        }
        if (card5.getColor() == ColorENUM.WILD) {
            wilds++;
        }
        if (wilds > 2) {
            return false;
        }
        return true;
    }
}
