package com.example.tyudy.ticket2rideclient.common.cities;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by Trevor on 3/8/2017.
 */

public class City implements Serializable, Comparable {
    /**
     * A map of the cities this city is connected to, with the
     * values being the class Path
     */
    private String mCityName;
    private HashSet<City> mConnectedCities;
    private ArrayList<Path> mPaths;
    private float xPosScale;
    private float yPosScale;


    /**
     * Initialize a city
     * @param cityName - Name of the city
     * @param xPercentage - horizontal percentage of the screen where the city sits
     * @param yPercentage - vertical percentage of the screen where the city sits
     */
    public City(String cityName, float xPercentage, float yPercentage) {
        mConnectedCities = new HashSet<>();
        mCityName = cityName;
        xPosScale = xPercentage;
        yPosScale = yPercentage;
        mPaths = new ArrayList<>();
    }

    public City(){

    }

    /**
     * Addes a city to this city instances array of connectedCities.
     * If the same cityToAdd is already in the connectedCities set it will not be added
     * @param cityToAdd - the city to add this city instances connected cities
     * @return - the city instance that the parameter is being added to. This is so we can do calls such as...
     *              ex. NewYorkCity.addConnectedCity(Boston).addConnectedCity(WashingtonDC)
     */
    public City addConnectedCity(City cityToAdd){
        mConnectedCities.add(cityToAdd);
        return this;
    }

    /**
     * Converts the cities to an array from a set
     * @return - an iterable set of connected cities.
     */
    public ArrayList getConnectedCityAsArray(){
        return new ArrayList<>(mConnectedCities);
    }


    public float getxPosScale(){
        return xPosScale;
    }

    public float getyPosScale(){
        return yPosScale;
    }

    /**
     * A function to find if a city is connected to another
     */
    public String getCityName() { return mCityName; }
    public ArrayList<Path> getPaths() { return mPaths; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof City)) return false;

        City city = (City) obj;
        if (!city.mPaths.equals(this.mPaths)) return false;
        if (!city.mCityName.equals(this.mCityName)) return false;

        return super.equals(obj);
    }

    @Override
    public int compareTo(Object o)
    {
        City compare = (City) o;
        if (this.getCityName().compareTo(compare.getCityName()) > 0) {
            return 1;
        }
        else if (this.getCityName().compareTo(compare.getCityName()) < 0) {
            return -1;
        }
        else return 0;
    }
}

