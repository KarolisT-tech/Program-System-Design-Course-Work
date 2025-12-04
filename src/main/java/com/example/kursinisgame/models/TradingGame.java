package com.example.kursinisgame.models;

public class TradingGame {

    private Player player;

    public TradingGame(Player player) {
        this.player = player;
    }

    public boolean isGameEnd(){
        if((player.getBalance() == 0 && player.getAssetQnt() == 0) || player.getMovesLeft() == 0){
            return true;
        }
        return false;
    }

}
