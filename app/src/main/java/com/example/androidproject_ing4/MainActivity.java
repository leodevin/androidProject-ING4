package com.example.androidproject_ing4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button gameInfo;

    //vars for recyclerview
    private ArrayList<String> adversaires = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();

        gameInfo = (Button)findViewById(R.id.addGameButton);

        gameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameInfo.class);
                startActivity(intent);
            }
        });

    }

    private void initDatas(){
        Log.d(TAG, "initDatas launched");
        adversaires.add("xaviou");
        adversaires.add("Thomas");
        adversaires.add("Victor");
        adversaires.add("Greg");
        adversaires.add("Jbou");
        adversaires.add("Tantoune");
        dates.add("12/07/2019");
        dates.add("3/02/2019");
        dates.add("12/01/2020");
        dates.add("18/07/2017");
        dates.add("28/02/2019");
        dates.add("7/11/2019");
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initDatas launched");
        RecyclerView recyclerView = findViewById(R.id.gameList);
        gameListViewAdapter adapter = new gameListViewAdapter( dates, adversaires, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
