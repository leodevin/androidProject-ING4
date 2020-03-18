package com.example.androidproject_ing4.outils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseSQLite extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseMySQL";

    // DATABASE & TABLE
    private static final String DB_NAME = "CoachDataBase.db";
    private static final String TABLE_LOCALISATIONS = "Localisations";
    private static final String TABLE_SETS = "Sets";
    private static final String TABLE_STATISTIQUES = "Statistiques";
    private static final String TABLE_PHOTOS = "Photos";
    private static final String TABLE_MATCHS = "Matchs";
    private static final int VERSION = 8;

    // FIELDS
    private static final String COLUMS_id = "Id";

    // LOCALISATIONS
    private static final String COLUMS_latitude = "latitude";
    private static final String COLUMS_longitude = "longitude";

    // SETS
    private static final String COLUMS_un = "un";
    private static final String COLUMS_deux = "deux";
    private static final String COLUMS_trois = "trois";
    private static final String COLUMS_quatre = "quatre";
    private static final String COLUMS_cinq = "cinq";

    // STATISTIQUES
    private static final String COLUMS_NbPtsGagnes = "Nombre_Points_Gagnes";
    private static final String COLUMS_PremieresBalles = "Premieres_Balles";
    private static final String COLUMS_Aces = "Aces";
    private static final String COLUMS_DoublesFautes = "Doubles_Fautes";
    private static final String COLUMS_PremieresBallesGagnes = "Premieres_Balles_Gagnes";
    private static final String COLUMS_CoupsDroitsGagnants = "Coups_Droits_Gagnants";
    private static final String COLUMS_NombreDeJeuGagnes = "Nombre_de_Jeu_Gagnes";
    private static final String COLUMS_FautesDirects = "Fautes_Directs";

    // PHOTOS
    private static final String COLUMS_Path = "Path";
    private static final String COLUMS_IdMatchs = "IdMatchs";

    // MATCHS
    private static final String COLUMS_Date = "Date";
    private static final String COLUMS_Joueur = "Joueur";
    private static final String COLUMS_Adversaire = "Adversaire";
    private static final String COLUMS_Duree = "Duree";
    private static final String COLUMS_Localisation = "Localisation";
    private static final String COLUMS_SetsJoueur = "SetsJoueur";
    private static final String COLUMS_SetsAdversaire = "SetsAdversaire";
    private static final String COLUMS_StatistiquesJoueur = "StatistiquesJoueur";
    private static final String COLUMS_StatistiquesAdversaire = "StatistiquesAdversaire";

    SQLiteDatabase database;

    // Constructor
    public DataBaseSQLite(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    /*----------------------------------------------------------------------------------------------*/
    /* INITIALISATION TABLES */
    /*----------------------------------------------------------------------------------------------*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LOCALISATIONS + " ( "
                + COLUMS_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_latitude + " DOUBLE, "
                + COLUMS_longitude + " DOUBLE);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SETS + " ( "
                + COLUMS_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_un + " INTEGER, "
                + COLUMS_deux + " INTEGER, "
                + COLUMS_trois + " INTEGER, "
                + COLUMS_quatre + " INTEGER, "
                + COLUMS_cinq + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATISTIQUES + " ( "
                + COLUMS_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_NbPtsGagnes + " INTEGER, "
                + COLUMS_PremieresBalles + " INTEGER, "
                + COLUMS_Aces + " INTEGER, "
                + COLUMS_DoublesFautes + " INTEGER, "
                + COLUMS_PremieresBallesGagnes + " INTEGER, "
                + COLUMS_CoupsDroitsGagnants + " INTEGER, "
                + COLUMS_NombreDeJeuGagnes + " INTEGER, "
                + COLUMS_FautesDirects + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PHOTOS + " ( "
                + COLUMS_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_Path + " TEXT, "
                + COLUMS_IdMatchs + " INTEGER, "
                + "FOREIGN KEY (" + COLUMS_IdMatchs + ") REFERENCES " + TABLE_MATCHS + " (" + COLUMS_id + "));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MATCHS + " ( "
                + COLUMS_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_Date + " TEXT, "
                + COLUMS_Joueur + " TEXT, "
                + COLUMS_Adversaire + " TEXT, "
                + COLUMS_Duree + " TEXT, "
                + COLUMS_Localisation + " INTEGER, "
                + COLUMS_SetsJoueur + " INTEGER, "
                + COLUMS_SetsAdversaire + " INTEGER, "
                + COLUMS_StatistiquesJoueur + " INTEGER, "
                + COLUMS_StatistiquesAdversaire + " INTEGER, "
                + "FOREIGN KEY (" + COLUMS_Localisation + ") REFERENCES " + TABLE_LOCALISATIONS + " (" + COLUMS_id + "), "
                + "FOREIGN KEY (" + COLUMS_SetsJoueur + ") REFERENCES " + TABLE_SETS + " (" + COLUMS_id + "), "
                + "FOREIGN KEY (" + COLUMS_SetsAdversaire + ") REFERENCES " + TABLE_SETS + " (" + COLUMS_id + "), "
                + "FOREIGN KEY (" + COLUMS_StatistiquesJoueur + ") REFERENCES " + TABLE_STATISTIQUES + " (" + COLUMS_id + "), "
                + "FOREIGN KEY (" + COLUMS_StatistiquesAdversaire + ") REFERENCES " + TABLE_STATISTIQUES + " (" + COLUMS_id + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALISATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTIQUES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHS);
        onCreate(db);
    }

    /*----------------------------------------------------------------------------------------------*/
    /* ADD NEW TABLES */
    /*----------------------------------------------------------------------------------------------*/

    // Ajout d'une localisation pour un match
    public boolean addLocalisation(int id, double latitude, double longitude) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_id, id);
        contentValues.put(COLUMS_latitude, latitude);
        contentValues.put(COLUMS_longitude, longitude);
        long result = database.insert(TABLE_LOCALISATIONS, null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    // Ajout des sets pour un match
    public boolean addSets(int id, int un, int deux, int trois, int quatre, int cinq) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_id, id);
        contentValues.put(COLUMS_un, un);
        contentValues.put(COLUMS_deux, deux);
        contentValues.put(COLUMS_trois, trois);
        contentValues.put(COLUMS_quatre, quatre);
        contentValues.put(COLUMS_cinq, cinq);
        long result = database.insert(TABLE_SETS, null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    // Ajout d'une statistique pour un match
    public boolean addStatistiques(int id, int nbPtsGagnes, int premieresBalles, int aces, int doublesFautes, int premieresBallesGagnes, int coupsDroitsGagnants, int nombreDeJeuGagnes, int fautesDirects) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_id, id);
        contentValues.put(COLUMS_NbPtsGagnes, nbPtsGagnes);
        contentValues.put(COLUMS_PremieresBalles, premieresBalles);
        contentValues.put(COLUMS_Aces, aces);
        contentValues.put(COLUMS_DoublesFautes, doublesFautes);
        contentValues.put(COLUMS_PremieresBallesGagnes, premieresBallesGagnes);
        contentValues.put(COLUMS_CoupsDroitsGagnants, coupsDroitsGagnants);
        contentValues.put(COLUMS_NombreDeJeuGagnes, nombreDeJeuGagnes);
        contentValues.put(COLUMS_FautesDirects, fautesDirects);
        long result = database.insert(TABLE_STATISTIQUES, null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    // Ajout d'une statistique pour un match
    public boolean addPhoto(int id, String path, int idMatchs) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_id, id);
        contentValues.put(COLUMS_Path, path);
        contentValues.put(COLUMS_IdMatchs, idMatchs);
        long result = database.insert(TABLE_PHOTOS, null, contentValues);

        if (result == -1)return false;
        else return true;
    }

    // Ajout d'un match
    public boolean addMatch(int id, String date,String joueur, String adversaire, String duree, int localisation, int sets1, int sets2, int stats1, int stats2) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_id, id);
        contentValues.put(COLUMS_Date, date);
        contentValues.put(COLUMS_Joueur, joueur);
        contentValues.put(COLUMS_Adversaire, adversaire);
        contentValues.put(COLUMS_Duree, duree);
        contentValues.put(COLUMS_Localisation, localisation);
        contentValues.put(COLUMS_SetsJoueur, sets1);
        contentValues.put(COLUMS_SetsAdversaire, sets2);
        contentValues.put(COLUMS_StatistiquesJoueur, stats1);
        contentValues.put(COLUMS_StatistiquesAdversaire, stats2);
        long result = database.insert(TABLE_MATCHS, null, contentValues);

        if (result == -1) return false;
        else return true;
    }


    /*----------------------------------------------------------------------------------------------*/
                                        /* REQUETES SQL */
    /*----------------------------------------------------------------------------------------------*/

    // GETTERS *ALL*
    public Cursor getNomJoueur() {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_Joueur + " FROM " + TABLE_MATCHS;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public ArrayList<String> getNomsPlayers(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_Joueur + ", " + COLUMS_Adversaire + " FROM " + TABLE_MATCHS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);

        ArrayList<String> nomsPlayers = new ArrayList<>();
        if (cursor.moveToFirst()){
            if (!cursor.isNull(0) && !cursor.isNull(1)){
                nomsPlayers.add(String.valueOf(cursor.getString(0)));
                nomsPlayers.add(String.valueOf(cursor.getString(1)));
            }
        }
        return nomsPlayers;
    }

    public Cursor getIdsMatchs() {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_id + " FROM " + TABLE_MATCHS;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public Cursor getAllMatchs() {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_Date + "," + COLUMS_Adversaire + " FROM " + TABLE_MATCHS;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public ArrayList<String> getSetsIdFromMatch(int id){
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_SetsJoueur + "," + COLUMS_SetsAdversaire + "," + COLUMS_Duree + " FROM " + TABLE_MATCHS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);

        ArrayList<String> idSets = new ArrayList<>();
        if (cursor.moveToFirst()){
            if (!cursor.isNull(0) && !cursor.isNull(1) && !cursor.isNull(2)){
                idSets.add(String.valueOf(cursor.getInt(0)));
                idSets.add(String.valueOf(cursor.getInt(1)));
                idSets.add(cursor.getString(2));
            }
        }
        return idSets;
    }

    public ArrayList<String> getStatsIdFromMatch(int id){
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_StatistiquesJoueur + "," + COLUMS_StatistiquesAdversaire + " FROM " + TABLE_MATCHS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);

        ArrayList<String> idStats = new ArrayList<>();
        if (cursor.moveToFirst()){
            if (!cursor.isNull(0) && !cursor.isNull(1)){
                idStats.add(String.valueOf(cursor.getInt(0)));
                idStats.add(String.valueOf(cursor.getInt(1)));
            }
        }
        return idStats;
    }

    // GETTERS BY ID
    public ArrayList<Double> getLocalisationById(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_latitude + ", " + COLUMS_longitude + " FROM " + TABLE_LOCALISATIONS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);

        ArrayList<Double> coordonnees = new ArrayList<>();
        if (cursor != null) cursor.moveToFirst();

        coordonnees.add(cursor.getDouble(cursor.getColumnIndex(COLUMS_latitude)));
        coordonnees.add(cursor.getDouble(cursor.getColumnIndex(COLUMS_longitude)));

        return coordonnees;
    }

    public Cursor getSetsById(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT * FROM " + TABLE_SETS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public Cursor getStatistiquesById(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT * FROM " + TABLE_STATISTIQUES + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public Cursor getPhotosById(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_Path + " FROM " + TABLE_PHOTOS + " WHERE " + COLUMS_IdMatchs + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public Cursor getMatchsById(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT * FROM " + TABLE_MATCHS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }

    public Cursor getMatchLocalisationById(int id) {
        database = this.getReadableDatabase();
        String requete = "SELECT " + COLUMS_Localisation + " FROM " + TABLE_MATCHS + " WHERE " + COLUMS_id + " = " + id;
        Cursor cursor = database.rawQuery(requete, null);
        return cursor;
    }
}
