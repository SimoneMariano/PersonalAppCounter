package com.personalcounter.sqlite;

public class RecordModel {
    String date, player1, player2, result;
    int id;

    public RecordModel(String date, String player1, String player2, String result) {
        this.date = date;
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
    }
    public String getdate() {
        return date;
    }
    public String getplayer1() {
        return player1;
    }
    public String getplayer2() {
        return player2;
    }
    public String getresult() {
        return result;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setPlayer1(String player1) {
        this.player1 = player1;
    }
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
