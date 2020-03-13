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
    Cursor cursor;

    //vars for recyclerview
    private ArrayList<String> adversaires = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameInfo = (Button)findViewById(R.id.addGameButton);
        nomJoueur = (TextView)findViewById(R.id.nomJoueur);
        dataBaseSQLite = new DataBaseSQLite(this);

        // A ENLEVER DES QU'ON A UNE DATABASE - INIT UN MATCH
        createDataBase();

        initDatas();

        gameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameInfo.class);
                startActivity(intent);
            }
        });
    }

    private void initDatas(){

        Cursor cursor_nomJoueur = dataBaseSQLite.getNomJoueur();
        if (cursor_nomJoueur.moveToFirst())
            nomJoueur.setText(cursor_nomJoueur.getString(0));

        Cursor cursor_idsMatch = dataBaseSQLite.getIdsMatchs();
        if (cursor_idsMatch.moveToFirst()){
            for (int i=0; i<cursor_idsMatch.getCount(); i++){
                Cursor cursor_photo = dataBaseSQLite.getPhotosById(cursor_idsMatch.getInt(0));
                if (cursor_photo.moveToFirst()){
                    // SET LES PHOTOS DE LA LISTE VIEW
                }
            }
        }

        Cursor cursor_match = dataBaseSQLite.getAllMatchs();
        if (cursor_match.moveToFirst()){
            for (int i=0; i<cursor_match.getCount(); i++){
                dates.add(cursor_match.getString(i));
                i++;
                adversaires.add(cursor_match.getString(i));
            }
        }
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initDatas launched");
        RecyclerView recyclerView = findViewById(R.id.gameList);
        gameListViewAdapter adapter = new gameListViewAdapter( dates, adversaires, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void createDataBase(){
        dataBaseSQLite.addLocalisation(10, 48.864716, 2.349014);
        dataBaseSQLite.addSets(20, 6, 3, 4, 6, 6);
        dataBaseSQLite.addSets(30, 4, 6, 6, 2, 1);
        dataBaseSQLite.addStatistiques(40, 100, 100, 100, 100, 100, 100, 100, 100);
        dataBaseSQLite.addStatistiques(50, 100, 100, 100, 100, 100, 100, 100, 100);
        dataBaseSQLite.addPhoto(1, "path", 1);
        dataBaseSQLite.addMatch(1, "12/04/2020","Roger", "Nadal", "2H33", 10, 20, 30, 40, 50);
    }
}
