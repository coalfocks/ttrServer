package com.example.tyudy.ticket2rideclient.common.cards;


import com.example.tyudy.ticket2rideclient.common.ColorENUM;

import java.io.Serializable;

/**
 * Created by zacheaton on 3/2/17.
 */

/**
 * this class is more of a train card stack of specific colors rather than a single card
 * the num corresponds to how many of the specific color you have
 *
 */
public class TrainCard implements iCard, Serializable {
    public ColorENUM color;
    public int num;
    public TrainCard(ColorENUM color){
        this.num = 0;
        this.color = color;
    }

    public TrainCard(){
        this.num = 0;
        this.color = color;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setColor(ColorENUM color) {
        this.color = color;
    }

    public ColorENUM getColor() {
        return color;
    }

    public void incNum() {
        this.num = this.num++;
    }
}
