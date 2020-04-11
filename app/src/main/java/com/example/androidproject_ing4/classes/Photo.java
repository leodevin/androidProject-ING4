package com.example.androidproject_ing4.classes;

public class Photo {

    private int id;
    private String path;
    private int idMatch;

    public Photo(int id, String path, int idMatch) {
        this.id = id;
        this.path = path;
        this.idMatch = idMatch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }
}
