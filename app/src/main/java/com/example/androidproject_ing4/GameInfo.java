package com.example.androidproject_ing4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GameInfo extends AppCompatActivity {

    private BottomNavigationView topNavigationView;
    private FrameLayout frameLayout;

    private StatsFragment statsFragment;
    private locationFragment locationFragment;
    private historiqueFragment historiqueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        frameLayout = (FrameLayout)findViewById(R.id.main_frame);
        topNavigationView = findViewById(R.id.navigation_view);

        statsFragment = new StatsFragment();
        locationFragment = new locationFragment();
        historiqueFragment = new historiqueFragment();

        topNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.statistiques:
                        setFragment(statsFragment);
                        break;
                    case R.id.location:
                        setFragment(locationFragment);
                        break;
                    case R.id.historique:
                        setFragment(historiqueFragment);
                        break;
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
