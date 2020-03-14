package com.example.androidproject_ing4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GameInfo extends AppCompatActivity {

    private BottomNavigationView topNavigationView;
    private FrameLayout frameLayout;
    private int idMatchSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        Intent intent = getIntent();
        idMatchSelected = intent.getIntExtra("id", 0);

        frameLayout = (FrameLayout)findViewById(R.id.main_frame);
        topNavigationView = findViewById(R.id.navigation_view);

        final Bundle bundle = new Bundle();
        bundle.putInt("id", idMatchSelected);

        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.statistiques:
                        Fragment fragmentStats = new StatsFragment();
                        fragmentStats.setArguments(bundle);
                        setFragment(fragmentStats);
                        return true;
                    case R.id.location:
                        Fragment fragmentLocation = new locationFragment();
                        fragmentLocation.setArguments(bundle);
                        setFragment(fragmentLocation);
                        return true;
                    case R.id.historique:
                        Fragment fragmentPhoto = new photoFragment();
                        fragmentPhoto.setArguments(bundle);
                        setFragment(fragmentPhoto);
                        return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
