package com.example.kursinisgame.models;

public class Player {

    public double balance;
    public int assetQnt;
    public int movesLeft;

    public Player(double balance) {
        this.balance = balance;
        this.assetQnt = 0;
        this.movesLeft = 15;
    }

    public double getBalance() {
        return balance;
    }

    public int getAssetQnt() {
        return assetQnt;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAssetQnt(int assetQnt) {
        this.assetQnt = assetQnt;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }
}
