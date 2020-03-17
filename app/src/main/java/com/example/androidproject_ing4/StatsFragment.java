package com.example.androidproject_ing4;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.androidproject_ing4.outils.DataBaseSQLite;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    private static final String TAG = "StatsFragment";

    private TableRow set_joueur;
    private TableRow set_adversaire;

    private DataBaseSQLite dataBaseSQLite;
    private int idMatchSelected;

    private ArrayList<Integer> stats = new ArrayList<>();
    private ArrayList<Integer> sets_joueur = new ArrayList<>();
    private ArrayList<Integer> sets_adversaire = new ArrayList<>();

    private ArrayList<Integer> idSets = new ArrayList<>();


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        try {
            idMatchSelected = Objects.requireNonNull(getArguments()).getInt("id");
        }catch (NullPointerException e){ Log.d(TAG, "id match actuel -> "+idMatchSelected); }

        dataBaseSQLite = new DataBaseSQLite(getContext());

        set_joueur = (TableRow) view.findViewById(R.id.sets_joueur);
        set_adversaire = (TableRow) view.findViewById(R.id.sets_adversaire);

        idSets = dataBaseSQLite.getSetsIdFromMatch(idMatchSelected);
        loadSetsPlayers(sets_joueur, idSets.get(0));
        loadSetsPlayers(sets_adversaire, idSets.get(1));

        createTableRowSets(set_joueur, sets_joueur);
        createTableRowSets(set_adversaire, sets_adversaire);

        return view;
    }

    private void createTableRowSets(TableRow players, ArrayList<Integer> setsMatch){
        for(int i=1; i<6;i++){
            TextView set = new TextView(getContext());
            set.setText(String.valueOf(setsMatch.get(i)));

            ((TableRow) players).addView(set);
        }
    }

    private void loadSetsPlayers(ArrayList<Integer> sets_players, int idSet){
        Cursor cursor_sets = dataBaseSQLite.getSetsById(idSet);
        if (cursor_sets.moveToFirst()){
            sets_players.add(cursor_sets.getInt(cursor_sets.getColumnIndex("Id")));
            sets_players.add(cursor_sets.getInt(cursor_sets.getColumnIndex("un")));
            sets_players.add(cursor_sets.getInt(cursor_sets.getColumnIndex("deux")));
            sets_players.add(cursor_sets.getInt(cursor_sets.getColumnIndex("trois")));
            sets_players.add(cursor_sets.getInt(cursor_sets.getColumnIndex("quatre")));
            sets_players.add(cursor_sets.getInt(cursor_sets.getColumnIndex("cinq")));
        }
    }

    private void loadStatsMatch(){
        Cursor cursor_stats = dataBaseSQLite.getStatistiquesById(idMatchSelected);
        if (cursor_stats.moveToFirst()){
            do{
                if (!cursor_stats.isNull(0)){
                    stats.add(cursor_stats.getInt(0));
                } else {
                    Log.d(TAG, "Plus de stats pour le match selectionné ");
                }
            }while (cursor_stats.moveToNext());
        }
;    }
}
