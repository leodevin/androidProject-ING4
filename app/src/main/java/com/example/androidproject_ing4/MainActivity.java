package com.example.androidproject_ing4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidproject_ing4.outils.DataBaseSQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private InternalMemoryController internalMemoryController = new InternalMemoryController();

    private Button gameInfo;
    private TextView nomJoueur;

    // Database
    DataBaseSQLite dataBaseSQLite;

    //vars for recyclerview
    private ArrayList<String> adversaires = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<Bitmap> photosDuMatch = new ArrayList<>();
    private ArrayList<Integer> idMatchs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameInfo = (Button)findViewById(R.id.addGameButton);
        nomJoueur = (TextView)findViewById(R.id.nomJoueur);
        dataBaseSQLite = new DataBaseSQLite(this);

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

        // ON RECUPERE LE NOM DU JOUEUR
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
                    if (internalMemoryController.readImage(getApplicationContext(),cursor_photo.getString(0)) != null){
                        photosDuMatch.add(internalMemoryController.readImage(getApplicationContext(),cursor_photo.getString(0)));
                    }
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
}
