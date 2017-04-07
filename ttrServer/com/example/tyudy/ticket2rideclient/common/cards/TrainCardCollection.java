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
public class TrainCardCollection implements iCard, Serializable {
    public ColorENUM color;
    public int num;
    public TrainCardCollection(ColorENUM color){
        this.num = 1;
        this.color = color;
    }

    public TrainCardCollection(){
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

    public boolean isEmpty() {
        if (this.num < 1) {
            return true;
        } else {
            return false;
        }
    }

    public void subtractCards(int numToSubtract){

    }
    public boolean isEmpty(){
        if (this.num == 0) {
            return true;
        } else if (this.num < 0) {
            System.err.print("Shouldn't be negative!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Subtract the given amount of cards from the train cards collection
     * @param numToSubtract - this is the number of train cards to subtract from this collection of cards
     */
    public void subtractCards(int numToSubtract){
        this.num -= numToSubtract;
    }
}
