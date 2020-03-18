package com.example.androidproject_ing4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidproject_ing4.outils.DataBaseSQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button gameInfo;
    private TextView nomJoueur;

    // Database
    DataBaseSQLite dataBaseSQLite;

    //vars for recyclerview
    private ArrayList<String> adversaires = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Integer> photosDuMatch = new ArrayList<>();
    private ArrayList<Integer> idMatchs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameInfo = (Button)findViewById(R.id.addGameButton);
        nomJoueur = (TextView)findViewById(R.id.nomJoueur);
        dataBaseSQLite = new DataBaseSQLite(this);

        // A ENLEVER DES QU'ON A UNE DATABASE - INIT UN MATCH
        //createFirstMatch();
        //createSecondMatch();

        initDatas();

        gameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addGameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDatas(){

        // ON RECUPERE LE NOM DU JOUEUR DU COACH
        Cursor cursor_nomJoueur = dataBaseSQLite.getNomJoueur();
        if (cursor_nomJoueur.moveToFirst())
            nomJoueur.setText(cursor_nomJoueur.getString(0));

        // ON RECUPERE L'ID DES MATCHS POUR TROUVER LEURS PHOTOS
        Cursor cursor_idsMatch = dataBaseSQLite.getIdsMatchs();
        if (cursor_idsMatch.moveToFirst()){
            for (int i=0; i<cursor_idsMatch.getCount(); i++){
                idMatchs.add(cursor_idsMatch.getInt(cursor_idsMatch.getColumnIndex("Id")));
                Cursor cursor_photo = dataBaseSQLite.getPhotosById(cursor_idsMatch.getInt(cursor_idsMatch.getColumnIndex("Id")));
                if (cursor_photo.moveToFirst()){
                    photosDuMatch.add(getResources().getIdentifier("drawable/"+cursor_photo.getString(0), "id", this.getPackageName()));
                }
                cursor_idsMatch.moveToNext();
            }
        }

        // ON RECUPERE TOUTES LES DONNEES IMPORTANTE DES MATCHS
        Cursor cursor_match = dataBaseSQLite.getAllMatchs();
        if (cursor_match.moveToFirst()){
            do {
                dates.add(cursor_match.getString(cursor_match.getColumnIndex("Date")));
                adversaires.add(cursor_match.getString(cursor_match.getColumnIndex("Adversaire")));
            }while (cursor_match.moveToNext());
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initDatas launched");
        RecyclerView recyclerView = findViewById(R.id.gameList);
        gameListViewAdapter adapter = new gameListViewAdapter(idMatchs ,dates, adversaires, photosDuMatch,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createFirstMatch(){
        dataBaseSQLite.addLocalisation(10, 48.864716, 2.349014);
        dataBaseSQLite.addSets(20, 6, 3, 4, 6, 6);
        dataBaseSQLite.addSets(30, 4, 6, 6, 2, 1);
        dataBaseSQLite.addStatistiques(40, 78, 23, 3, 3, 18, 32, 25, 3);
        dataBaseSQLite.addStatistiques(50, 61, 15, 6, 4, 15, 22, 19, 1);
        dataBaseSQLite.addPhoto(1, "tennis1", 1);
        dataBaseSQLite.addPhoto(2, "tennis2", 1);
        dataBaseSQLite.addPhoto(3, "tennis3", 1);
        dataBaseSQLite.addPhoto(4, "tennis4", 1);
        dataBaseSQLite.addPhoto(5, "tennis5", 1);
        dataBaseSQLite.addMatch(1, "12/04/2020","Roger Federrer", "Nadal", "2h33", 10, 20, 30, 40, 50);
    }

    private void createSecondMatch(){
        dataBaseSQLite.addLocalisation(11, 43.864716, 4.349014);
        dataBaseSQLite.addSets(21, 6, 4, 7, 5, 6);
        dataBaseSQLite.addSets(31, 2, 6, 5, 7, 4);
        dataBaseSQLite.addStatistiques(41, 48, 33, 2, 4, 13, 22, 28, 4);
        dataBaseSQLite.addStatistiques(51, 51, 25, 3, 2, 15, 32, 24, 3);
        dataBaseSQLite.addPhoto(6, "tennis1", 2);
        dataBaseSQLite.addPhoto(7, "tennis2", 2);
        dataBaseSQLite.addPhoto(8, "tennis3", 2);
        dataBaseSQLite.addPhoto(9, "tennis4", 2);
        dataBaseSQLite.addPhoto(10, "tennis5", 2);
        dataBaseSQLite.addMatch(2, "15/05/2020","Roger Federrer", "Monfils", "2h01", 11, 21, 31, 41, 51);
    }
}
