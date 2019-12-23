package com.example.memorygame;

public class HighScore implements Comparable<HighScore> {
    private String name = "";
    private Integer highscore = 0;

    public HighScore(String name, Integer highscore) {
        this.name = name;
        this.highscore= highscore;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHighscore(Integer highscore) {
        highscore = highscore;
    }

    public Integer getHighscore() {
        return highscore;
    }

    public int compareTo(HighScore other){
        return(this.highscore.compareTo(other.highscore));
    }




}