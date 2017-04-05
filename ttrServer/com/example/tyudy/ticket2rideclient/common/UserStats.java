package com.example.tyudy.ticket2rideclient.common;

import java.io.Serializable;

/**
 * Created by Trevor on 4/4/2017.
 */

public class UserStats implements Serializable {

    private String mUsername;
    private int mRoutePoints;
    private int mDestPoints;
    private int mNegDestPoints;
    private int mLongestRoutePoints;

    public UserStats(String name, int route, int dest, int neg, int longest) {
        mUsername = name;
        mRoutePoints = route;
        mDestPoints = dest;
        mNegDestPoints = neg;
        mLongestRoutePoints = longest;
    }

    public UserStats(String name) {
        mUsername = name;
        mRoutePoints = 0;
        mDestPoints = 0;
        mNegDestPoints = 0;
        mLongestRoutePoints = 0;
    }

    public void setName(String name) { mUsername = name; }

    public void setRoutePoints(int pts) { mRoutePoints = pts; }

    public void setDestPoints(int pts) { mDestPoints = pts; }

    public void setNegPoints(int negPts) { mNegDestPoints = negPts; }

    public void setLongestRoutePoints(int pts) { mLongestRoutePoints = pts; }

    public String getName() { return mUsername; }

    public int getRoutePoints() { return mRoutePoints; }

    public int getDestPoints() { return mDestPoints; }

    public int getNegDestPoints() { return mNegDestPoints; }

    public int getLongestRoutePoints() { return mLongestRoutePoints; }

    public int getTotalPoints() {
        return mRoutePoints + mDestPoints +
                mNegDestPoints + mLongestRoutePoints;
    }
}