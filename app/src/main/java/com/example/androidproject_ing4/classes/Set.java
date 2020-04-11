package com.example.androidproject_ing4.classes;

public class Set {

    private int id;
    private int un;
    private int deux;
    private int trois;
    private int quatre;
    private int cinq;

    public Set(int id, int un, int deux, int trois, int quatre, int cinq) {
        this.id = id;
        this.un = un;
        this.deux = deux;
        this.trois = trois;
        this.quatre = quatre;
        this.cinq = cinq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUn() {
        return un;
    }

    public void setUn(int un) {
        this.un = un;
    }

    public int getDeux() {
        return deux;
    }

    public void setDeux(int deux) {
        this.deux = deux;
    }

    public int getTrois() {
        return trois;
    }

    public void setTrois(int trois) {
        this.trois = trois;
    }

    public int getQuatre() {
        return quatre;
    }

    public void setQuatre(int quatre) {
        this.quatre = quatre;
    }

    public int getCinq() {
        return cinq;
    }

    public void setCinq(int cinq) {
        this.cinq = cinq;
    }
}
