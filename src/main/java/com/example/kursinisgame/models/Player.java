package com.example.kursinisgame.models;

public class Player {

    private static final int INITIAL_MOVES = 15;
    private static final int INITIAL_ASSET_QNT = 0;

    private double balance;
    private int assetQnt;
    private int movesLeft;

    public Player(double balance) {
        this.balance = balance;
        this.assetQnt = INITIAL_ASSET_QNT;
        this.movesLeft = INITIAL_MOVES;
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

    public void sell(int quantity, double price, double taxRate) {
        double totalProfit = quantity * price;
        assetQnt -= quantity;
        balance = balance + totalProfit - (totalProfit * taxRate);
    }
}
