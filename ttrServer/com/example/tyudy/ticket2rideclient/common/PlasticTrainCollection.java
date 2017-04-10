package com.example.tyudy.ticket2rideclient.common;

import com.example.tyudy.ticket2rideclient.common.cities.Path;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tyudy on 3/28/17.
 */

public class PlasticTrainCollection implements Serializable {

    final int TRAIN_COLLECTION_MAX_SIZE = 10;
    ArrayList<PlasticTrain> mMyTrains;
    int mCollectionColor;

    /**
     * Don't pass an ENUM in here. They're kind pointless. We should use Color.RED for example from here on out
     */
    public PlasticTrainCollection(){
        mMyTrains = new ArrayList<>();
        for(int i = 0; i < TRAIN_COLLECTION_MAX_SIZE; i++) { //Initialize with 45 train pieces
            mMyTrains.add(new PlasticTrain());
        }
    }

    public int getCollectionColor(){
        return mCollectionColor;
    }
    public int getSize(){
        return mMyTrains.size();
    }

    /**
     * remove the correct number of Train pieces "to place on the board".
     * @param numberToRemove - The number of cars to be removed
     */
    public void removeTrains(int numberToRemove){
        for(int i = 0; i < numberToRemove; i++) {

            if (mMyTrains.isEmpty()) {
                return;
            }

            mMyTrains.remove(0);
        }
    }



    // Class for each individual train--------------------------------------------------------
    private class PlasticTrain implements Serializable {

        PlasticTrain(){

        }
    }
}
