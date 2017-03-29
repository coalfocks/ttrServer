package com.example.tyudy.ticket2rideclient.common;

import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCard;
import com.example.tyudy.ticket2rideclient.common.cities.Path;
import com.example.tyudy.ticket2rideclient.common.decks.DestinationCardDeck;
import com.example.tyudy.ticket2rideclient.common.decks.TrainCardDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by colefox on 2/6/17.
 */
public class TTRGame implements Serializable
{
    private int inProgress;
    private int gameID;
    private int ownerID;
    private String ownerUsername;
    private Set<User> players = new TreeSet<User>();
    private int mTurnIndex = 0;
    private TrainCardDeck myTrainDeck;
    private TrainCardDeck mTrainDiscardDeck;
    private DestinationCardDeck myDestDeck;
    private DestinationCardDeck mDestDiscardDeck;

    public TTRGame()
    {
        mTrainDiscardDeck = new TrainCardDeck();
        mDestDiscardDeck = new DestinationCardDeck();
    }

    public void setMyTrainDeck(TrainCardDeck myTrainDeck) {
        this.myTrainDeck = myTrainDeck;
    }

    public void setMyDestDeck(DestinationCardDeck myDestDeck) {
        this.myDestDeck = myDestDeck;
    }

    public TrainCardDeck getMyTrainDeck() {
        return myTrainDeck;
    }

    public DestinationCardDeck getMyDestDeck() {
        return myDestDeck;
    }

    public int getInProgress()
    {
        return inProgress;
    }

    public void setInProgress(int inProgress)
    {
        this.inProgress = inProgress;
    }

    public int getOwnerID()
    {
        return ownerID;
    }

    public void setOwnerID(int ownerID)
    {
        this.ownerID = ownerID;
    }

    public void addPlayer(User player)
    {
        players.add(player);
    }

    public int getNumPlayers()
    {
        return players.size();
    }

    public String getOwnerUsername()
    {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername)
    {
        this.ownerUsername = ownerUsername;
    }

    public Set<User> getUsers()
    {
        return players;
    }

    public void setPlayers(Set<User> players)
    {
        this.players = players;
    }

    public int getGameID()
    {
        return gameID;
    }

    public void setGameID(int gameID)
    {
        this.gameID = gameID;
    }

    public int getmTurnIndex()
    {
        return mTurnIndex;
    }

    public void setmTurnIndex(int mTurnIndex)
    {
        this.mTurnIndex = mTurnIndex;
    }

    /**
     * Updates the ClientModels given path to have an owner and adds the corresponding points to the paths owner user.
     * @param path - the path to update in the client model
     */
    public void updateClaimedPath(Path path) {

//        for (Path p : ClientModel.SINGLETON.getAllPaths()) {
//            if (p.getName().equals(path.getName())) {
//                p.setOwner(path.getOwner());
//                for (User u : this.getUsers()) {
//                    if (u.getPlayerID() == path.getOwner().getPlayerID()) {
//                        u.addPoints(path.getPoints());
//                    }
//                }
//            }
//        }

    }
    // dealTrainCard used by the server
    public void dealTrainCard(int playerID){
        TrainCard card = (TrainCard) myTrainDeck.getCard();
        for (User u : players)
        {
            if (u.getPlayerID() == playerID)
            {
                u.addTrainCard(card);
            }
        }
    }

    public void dealTrainCard(User u){
//        TrainCard myCard = (TrainCard)  getMyTrainDeck().getCard();
//        u.addTrainCard(myCard);
    }

    public void addToTrainDiscard(TrainCard card)
    {
        mTrainDiscardDeck.addCard(card);
    }

    public void dealDestCard(User u){
        DestinationCard myCard = (DestinationCard) getMyDestDeck().getCard();
        u.addDestinationCard(myCard);
    }

    public void addToDestDiscard(DestinationCard card)
    {
        mDestDiscardDeck.addCard(card);
    }

    public TrainCardDeck getTrainDiscardDeck() { return mTrainDiscardDeck; }

    public DestinationCardDeck getDestDiscardDeck() { return mDestDiscardDeck; }

    public void clearTrainDiscardDeck() { mTrainDiscardDeck.getDeck().clear(); }

    public void clearDestDiscardDeck() { mDestDiscardDeck.getDeck().clear(); }

    public void changeTurn() {
        this.mTurnIndex++;
        mTurnIndex %= this.players.size();
    }

    public int getWhoTurn() {
        ArrayList<User> arr = new ArrayList<>(players);
        return arr.get(mTurnIndex).getPlayerID();
    }

    public void setUsers(Set<User> users) {
        this.players = users;
    }
}
