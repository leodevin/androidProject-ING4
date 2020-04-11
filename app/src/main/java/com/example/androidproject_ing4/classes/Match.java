package com.example.androidproject_ing4.classes;

public class Match {

    private int id;
    private String date;
    private String joueur;
    private String adversaire;
    private String duree;
    private int localisation;
    private int setsJoueur;
    private int setsAdversaire;
    private int statistiquesJoueur;
    private int statistiquesAdversaire;

    public Match(int id, String date, String joueur, String adversaire, String duree, int localisation, int setsJoueur, int setsAdversaire, int statistiquesJoueur, int statistiquesAdversaire) {
        this.id = id;
        this.date = date;
        this.joueur = joueur;
        this.adversaire = adversaire;
        this.duree = duree;
        this.localisation = localisation;
        this.setsJoueur = setsJoueur;
        this.setsAdversaire = setsAdversaire;
        this.statistiquesJoueur = statistiquesJoueur;
        this.statistiquesAdversaire = statistiquesAdversaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public String getAdversaire() {
        return adversaire;
    }

    public void setAdversaire(String adversaire) {
        this.adversaire = adversaire;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getLocalisation() {
        return localisation;
    }

    public void setLocalisation(int localisation) {
        this.localisation = localisation;
    }

    public int getSetsJoueur() {
        return setsJoueur;
    }

    public void setSetsJoueur(int setsJoueur) {
        this.setsJoueur = setsJoueur;
    }

    public int getSetsAdversaire() {
        return setsAdversaire;
    }

    public void setSetsAdversaire(int setsAdversaire) {
        this.setsAdversaire = setsAdversaire;
    }

    public int getStatistiquesJoueur() {
        return statistiquesJoueur;
    }

    public void setStatistiquesJoueur(int statistiquesJoueur) {
        this.statistiquesJoueur = statistiquesJoueur;
    }

    public int getStatistiquesAdversaire() {
        return statistiquesAdversaire;
    }

    public void setStatistiquesAdversaire(int statistiquesAdversaire) {
        this.statistiquesAdversaire = statistiquesAdversaire;
    }
}
