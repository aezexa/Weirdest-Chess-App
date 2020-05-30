package main;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Comparator;

public class User {
    public static ArrayList<User> allUsers = new ArrayList <> (  );
    private static User whiteUser;
    private static User blackUser;
    private int timer;
    private String name;
    private String password;
    private int score;
    private int wins;
    private int draws;
    private int losses;
    private boolean isTurn;
    private boolean[][] userOwnership = new boolean[8][8];
    private int remainingUndo = 2;
    private ArrayList < String > moveHistory = new ArrayList <> ( );
    private ArrayList < String > killHistory = new ArrayList <> ( );

    User ( String name , String password ) {
        this.name = name;
        this.password = password;
    }

    public String getName () {
        return name;
    }

    String getPassword () {
        return password;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }

    public static void setWhiteUser ( User whiteUser ) {
        User.whiteUser = whiteUser;
    }

    public static User getWhiteUser () {
        return whiteUser;
    }

    public static void setBlackUser ( User blackUser ) {
        User.blackUser = blackUser;
    }

    public static User getBlackUser () {
        return blackUser;
    }

    public static User getUserWithName ( String name ) {
        for (User user : allUsers) {
            if ( user.getName ( ).equals ( name ) )
                return user;
        }
        return null;
    }

    public void secondPass () {
        timer--;
    }

    public int getTimer () {
        return timer;
    }

    public void setTimer ( int timer ) {
        this.timer = timer;
    }

    int getScore () {
        return score;
    }

    int getWins () {
        return wins;
    }

    int getDraws () {
        return draws;
    }

    int getLosses () {
        return losses;
    }

    public int getRemainingUndo () {
        return remainingUndo;
    }

    public ArrayList < String > getMoveHistory () {
        return moveHistory;
    }

    public ArrayList < String > getKillHistory () {
        return killHistory;
    }

    public boolean userOwnsSquare ( int row , int column ) {
        return userOwnership[row][column];
    }

    public boolean isTurn () {
        return isTurn;
    }

    public void addScore ( int amount ) {
        score += amount;
    }

    public void addWins () {
        wins++;
    }

    public void addDraws () {
        draws++;
    }

    public void addLosses () {
        losses++;
    }

    public void setRemainingUndo ( int remainingUndo ) {
        this.remainingUndo = remainingUndo;
    }

    public void setUserOwnsSquare ( int row , int column , boolean type ) {
        userOwnership[row][column] = type;
    }

    public void reduceUndo () {
        remainingUndo--;
    }

    public void setTurn ( boolean turn ) {
        isTurn = turn;
    }

    static class scoreboardSorter implements Comparator < User > {
        public int compare ( User user1 , User user2 ) {
            int scoreResult;
            if ( (scoreResult = Integer.compare ( user1.getScore ( ) , user2.getScore ( ) )) != 0 )
                return -1 * scoreResult;
            else {
                int winResult;
                if ( (winResult = Integer.compare ( user1.getWins ( ) , user2.getWins ( ) )) != 0 )
                    return -1 * winResult;
                else {
                    int drawResult;
                    if ( (drawResult = Integer.compare ( user1.getDraws ( ) , user2.getDraws ( ) )) != 0 )
                        return -1 * drawResult;
                    else {
                        int lossesResult;
                        if ( (lossesResult = Integer.compare ( user1.getLosses ( ) , user2.getLosses ( ) )) != 0 )
                            return lossesResult;
                        else
                            return user1.getName ( ).compareTo ( user2.getName ( ) );
                    }
                }
            }
        }
    }

    private SimpleIntegerProperty rankProperty;
    private SimpleStringProperty nameProperty;
    private SimpleIntegerProperty scoreProperty;
    private SimpleIntegerProperty winsProperty;
    private SimpleIntegerProperty drawsProperty;
    private SimpleIntegerProperty lossesProperty;

    public void makeScoreboardCompatible ( int rank ) {
        this.rankProperty = new SimpleIntegerProperty ( rank );
        this.nameProperty = new SimpleStringProperty ( name );
        this.scoreProperty = new SimpleIntegerProperty ( score );
        this.winsProperty = new SimpleIntegerProperty ( wins );
        this.drawsProperty = new SimpleIntegerProperty ( draws );
        this.lossesProperty = new SimpleIntegerProperty ( losses );
    }

    public void setRankProperty ( int rankProperty ) {
        this.rankProperty.set ( rankProperty );
    }

    public int getRankProperty () {
        return rankProperty.get ( );
    }

    public void setNameProperty ( String nameProperty ) {
        this.nameProperty.set ( nameProperty );
    }

    public void setScoreProperty ( int scoreProperty ) {
        this.scoreProperty.set ( scoreProperty );
    }

    public void setWinsProperty ( int winsProperty ) {
        this.winsProperty.set ( winsProperty );
    }

    public void setDrawsProperty ( int drawsProperty ) {
        this.drawsProperty.set ( drawsProperty );
    }

    public void setLossesProperty ( int lossesProperty ) {
        this.lossesProperty.set ( lossesProperty );
    }

    public String getNameProperty () {
        return nameProperty.get ( );
    }

    public int getScoreProperty () {
        return scoreProperty.get ( );
    }

    public int getWinsProperty () {
        return winsProperty.get ( );
    }

    public int getDrawsProperty () {
        return drawsProperty.get ( );
    }

    public int getLossesProperty () {
        return lossesProperty.get ( );
    }

}

