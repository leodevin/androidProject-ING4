package com.example.androidproject_ing4.classes;

public class Statistique {

    private int id;
    private int nbPtsGagnes;
    private int premieresBalles;
    private int aces;
    private int doublesFautes;
    private int premieresBallesGagnes;
    private int coupsDroitsGagnants;
    private int nombreDeJeuGagnes;
    private int fautesDirects;

    public Statistique(int id, int nbPtsGagnes, int premieresBalles, int aces, int doublesFautes, int premieresBallesGagnes, int coupsDroitsGagnants, int nombreDeJeuGagnes, int fautesDirects) {
        this.id = id;
        this.nbPtsGagnes = nbPtsGagnes;
        this.premieresBalles = premieresBalles;
        this.aces = aces;
        this.doublesFautes = doublesFautes;
        this.premieresBallesGagnes = premieresBallesGagnes;
        this.coupsDroitsGagnants = coupsDroitsGagnants;
        this.nombreDeJeuGagnes = nombreDeJeuGagnes;
        this.fautesDirects = fautesDirects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPtsGagnes() {
        return nbPtsGagnes;
    }

    public void setNbPtsGagnes(int nbPtsGagnes) {
        this.nbPtsGagnes = nbPtsGagnes;
    }

    public int getPremieresBalles() {
        return premieresBalles;
    }

    public void setPremieresBalles(int premieresBalles) {
        this.premieresBalles = premieresBalles;
    }

    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    public int getDoublesFautes() {
        return doublesFautes;
    }

    public void setDoublesFautes(int doublesFautes) {
        this.doublesFautes = doublesFautes;
    }

    public int getPremieresBallesGagnes() {
        return premieresBallesGagnes;
    }

    public void setPremieresBallesGagnes(int premieresBallesGagnes) {
        this.premieresBallesGagnes = premieresBallesGagnes;
    }

    public int getCoupsDroitsGagnants() {
        return coupsDroitsGagnants;
    }

    public void setCoupsDroitsGagnants(int coupsDroitsGagnants) {
        this.coupsDroitsGagnants = coupsDroitsGagnants;
    }

    public int getNombreDeJeuGagnes() {
        return nombreDeJeuGagnes;
    }

    public void setNombreDeJeuGagnes(int nombreDeJeuGagnes) {
        this.nombreDeJeuGagnes = nombreDeJeuGagnes;
    }

    public int getFautesDirects() {
        return fautesDirects;
    }

    public void setFautesDirects(int fautesDirects) {
        this.fautesDirects = fautesDirects;
    }
}
