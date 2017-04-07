package com.example.tyudy.ticket2rideclient.common;


import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCardCollection;
import com.example.tyudy.ticket2rideclient.common.cities.City;
import com.example.tyudy.ticket2rideclient.common.cities.Path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import static com.example.tyudy.ticket2rideclient.common.ColorENUM.BLACK;
import static com.example.tyudy.ticket2rideclient.common.ColorENUM.WHITE;

/**
 * Created by tyudy on 2/7/17.
 */

public class User implements Serializable, Comparable<User> {
    private String username;
    private String password;
    private int playerID;
    private int inGame;
    private int points = 0;
    private int longest = 0;
//    private IState currentState;

    private ColorENUM color;

    private ArrayList<Path> claimedPaths;
    private Map<ColorENUM, TrainCardCollection> colorCards;
    private ArrayList<DestinationCard> destCards;


    public User()
    {
        username = "";
        password = "";
        playerID = 0;
        inGame = 0;
        points = 0;
        destCards = new ArrayList<>();
        colorCards = new HashMap<ColorENUM, TrainCardCollection>();
        claimedPaths = new ArrayList<>();
        this.color = BLACK;
    }

    public User(String username, String password, int playerID, int inGame)
    {
        this.username = username;
        this.password = password;
        this.playerID = playerID;
        this.inGame = inGame;


        destCards = new ArrayList<>();
        //claimedPaths = new ArrayList<>();
        colorCards = new HashMap<ColorENUM, TrainCardCollection>();
        TrainCardCollection myCard = new TrainCardCollection();
        myCard.setColor(WHITE);
        this.addTrainCard(myCard);
        destCards = new ArrayList<>();
        colorCards = new HashMap<ColorENUM, TrainCardCollection>();
        this.color = BLACK;
    }


    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    public int getInGame()
    {
        return inGame;
    }

    public void setInGame(int inGame)
    {
        this.inGame = inGame;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public int getPoints()
    {
        return points;
    }

    public void addPoints(int pointValue) {
        this.points += pointValue;
    }

    @Override
    public int compareTo(User o)
    {
        if(this.playerID > o.getPlayerID()) {
            return 1;
        }
        else if (this.playerID < o.getPlayerID()) {
            return -1;
        }
        return 0;
    }

    public boolean addDestinationCard(DestinationCard card){
        return destCards.add(card);
    }

    public ArrayList<DestinationCard> getDestCards() { return destCards; }

    public void setDestCards(ArrayList<DestinationCard> cards) {
        this.destCards = cards;
    }

    //Cards stuff
    public void addTrainCard(TrainCardCollection card){
        TrainCardCollection c = colorCards.get(card.getColor());
        if(c != null) {
            c.incNum();
            colorCards.put(card.getColor(), c);
        }
        else{
            colorCards.put(card.getColor(), card);
        }
    }
    public ArrayList<TrainCardCollection> getTrainCards(){
        ArrayList<TrainCardCollection> arrayOfCards = new ArrayList<TrainCardCollection>(colorCards.values());
        return arrayOfCards;
    }


    /**
     * Get the TrainCardCollection(s) object for a specific color
     * @param - the color of card to search for
     * @return - TrainCardCollection object that has the given color
     */
    public TrainCardCollection getTrainCardsOfColor(ColorENUM color) {
        ArrayList<TrainCardCollection> arrayOfCards = getTrainCards();
        for(TrainCardCollection trainCardCollection : arrayOfCards){
            if (color == trainCardCollection.getColor()) {
                return trainCardCollection;
            }
        }
        return new TrainCardCollection(color);
    }

    /**
     * Remove the cards of the given color from the users total collection of cards
     */
    public void removeTrainCardsWithColor(ColorENUM color) {
        colorCards.remove(color);
    }

    public int getNumCards () {
        int total = 0;
        for (TrainCardCollection card : colorCards.values()) {
            total += card.getNum();
        }
        return total;
    }

    public void increasePoints(int addPoints) {
        points += Math.abs(addPoints);
    }

    public void decreasePoints(int subtractPoints) {
        points -= Math.abs(subtractPoints);

        // Can't go less than 0 points
        if (points < 0)
            points = 0;
    }

    public void setPoints(int newValue) {
        if (newValue > 0)
            points = newValue;
        else
            points = 0;
    }


    public ColorENUM getColor() {
        return color;
    }

    public void setColor(ColorENUM color) {
        this.color = color;
    }

    public void claimPath(Path p) {
        claimedPaths.add(p);
        addPoints(p.getPoints());
    }

    public ArrayList<Path> getClaimedPaths () {
        return claimedPaths;
    }

    public boolean haveCompletedRoute(DestinationCard card) {
        Boolean hasSrc = false;
        Boolean hasDest = false;
        Path srcPath = null;

        for (Path p : claimedPaths) {
            for (City city : p.getCities()) {
                if (city.getCityName().equals(card.getDestination().getSource())) {
                    hasSrc = true;
                    srcPath = p;
                } else if (city.getCityName().equals(card.getDestination().getDest())) {
                    hasDest = true;
                }
            }
        }

        if (!hasSrc || !hasDest) {
            return false;
        }

        return followPath(srcPath, claimedPaths, card.getDestination().getDest());
    }

    public void removeDestinationCard(DestinationCard card) {
        this.destCards.remove(card);
    }

    public void removeAllTrainCards(){
        colorCards = new HashMap<>();
    }

    public void removeAllDestinationCards(){
        destCards = new ArrayList<>();
    }

    public int getLongest()
    {
        return longest;
    }

    public void setLongest(int longest)
    {
        this.longest = longest;
    }

    public boolean followPath(Path path, ArrayList<Path> claimedPaths, String dest) {
        ArrayList<Path> newPaths = new ArrayList<>(claimedPaths);
        newPaths.remove(path);
        for (City city : path.getCities()) {
            if (city.getCityName().equals(dest)) {
                return true;
            }
        }
        for (Path p : newPaths) {
            if (p.containsCity(path.getCities().get(0)) ||
                    p.containsCity(path.getCities().get(1))) {
                return followPath(p, newPaths, dest);
            }
        }
        return false;
    }
}

