package org.example;

import java.util.ArrayList;

public class User {
    public static ArrayList<User> allUsers = new ArrayList <> (  );
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
        return userOwnership[row - 1][column - 1];
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
        userOwnership[row - 1][column - 1] = type;
    }

    public void reduceUndo () {
        remainingUndo--;
    }

    public void setTurn ( boolean turn ) {
        isTurn = turn;
    }

    public void addToMoveHistory ( String string ) {
        moveHistory.add ( string );
    }

    public void removeFromMoveHistory () {
        moveHistory.remove ( moveHistory.size ( ) - 1 );
    }

    public void addToKillHistory ( String string ) {
        killHistory.add ( string );
    }

    public void removeFromKillHistory () {
        killHistory.remove ( killHistory.size ( ) - 1 );
    }

    public void clearMoveAndKillHistory () {
        moveHistory.clear ( );
        killHistory.clear ( );
    }

    void remove () {
        System.out.println ( "removed " + name + " successfully" );
    }

}

