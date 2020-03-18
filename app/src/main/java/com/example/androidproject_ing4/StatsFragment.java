package com.example.androidproject_ing4;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.androidproject_ing4.outils.DataBaseSQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    private static final ArrayList<String> nomColones = new ArrayList<>();

    private static final String TAG = "StatsFragment";

    private TableLayout tab_stats;
    private TableRow set_joueur;
    private TableRow set_adversaire;
    private TextView duree;

    private DataBaseSQLite dataBaseSQLite;
    private int idMatchSelected;

    private ArrayList<Integer> stats_joueur = new ArrayList<>();
    private ArrayList<Integer> stats_adversaire = new ArrayList<>();
    private ArrayList<Integer> sets_joueur = new ArrayList<>();
    private ArrayList<Integer> sets_adversaire = new ArrayList<>();
    private ArrayList<String> nomsPlayers = new ArrayList<>();

    private ArrayList<String> idSets = new ArrayList<>();
    private ArrayList<String> idStats = new ArrayList<>();


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

        // Initialisation
        tab_stats = (TableLayout) view.findViewById(R.id.table_stats);
        set_joueur = (TableRow) view.findViewById(R.id.sets_joueur);
        set_adversaire = (TableRow) view.findViewById(R.id.sets_adversaire);
        duree = (TextView) view.findViewById(R.id.duree_value);

        // pour les noms du TableRow
        nomColones.add("Nombre Points Gagnes");
        nomColones.add("Premieres Balles");
        nomColones.add("Aces");
        nomColones.add("Doubles Fautes");
        nomColones.add("Premieres Balles Gagnes");
        nomColones.add("Coups Droits Gagnants");
        nomColones.add("Nombre de_Jeu_Gagnes");
        nomColones.add("Fautes_Directs");

        // Load data from database
        idSets = dataBaseSQLite.getSetsIdFromMatch(idMatchSelected);
        idStats = dataBaseSQLite.getStatsIdFromMatch(idMatchSelected);
        nomsPlayers = dataBaseSQLite.getNomsPlayers(idMatchSelected);

        loadSetsPlayers(sets_joueur, Integer.parseInt(idSets.get(0)));
        loadSetsPlayers(sets_adversaire, Integer.parseInt(idSets.get(1)));

        loadStatsMatch(stats_joueur, Integer.parseInt(idStats.get(0)));
        loadStatsMatch(stats_adversaire, Integer.parseInt(idStats.get(1)));

        // Creation de l'UI via data receive
        String dureeMatch = idSets.get(2) + " minutes";
        duree.setText(dureeMatch);

        createTableRowSets(set_joueur, sets_joueur,0);
        createTableRowSets(set_adversaire, sets_adversaire,1);

        initEnteteTableRow();
        for (int i=0; i<8; i++){
            initTableRows(i);
        }

        return view;
    }

    private void initEnteteTableRow(){
        TableRow tableRow = new TableRow(getContext());

        TextView entete1 = new TextView(getContext());
        entete1.setText("");
        ((TableRow) tableRow).addView(entete1);

        TextView entete2 = new TextView(getContext());
        entete2.setText(nomsPlayers.get(0));
        ((TableRow) tableRow).addView(entete2);

        TextView entete3 = new TextView(getContext());
        entete3.setText(nomsPlayers.get(1));
        ((TableRow) tableRow).addView(entete3);

        TableRow.LayoutParams tableLayout = new TableRow.LayoutParams();
        tableLayout.width = 0;
        tableRow.setLayoutParams(tableLayout);
        tab_stats.addView(tableRow);
    }

    private void initTableRows(int i){
        TableRow tableRow = new TableRow(getContext());

        TextView stat1 = new TextView(getContext());
        stat1.setText(nomColones.get(i));
        ((TableRow) tableRow).addView(stat1);

        TextView stat2 = new TextView(getContext());
        stat2.setText(String.valueOf(stats_joueur.get(i+1)));
        ((TableRow) tableRow).addView(stat2);

        TextView stat3 = new TextView(getContext());
        stat3.setText(String.valueOf(stats_adversaire.get(i+1)));
        ((TableRow) tableRow).addView(stat3);

        TableRow.LayoutParams tableLayout = new TableRow.LayoutParams();
        tableLayout.width = 0;
        tableRow.setLayoutParams(tableLayout);
        tab_stats.addView(tableRow);
    }

    private void createTableRowSets(TableRow players, ArrayList<Integer> setsMatch, int id){
        TextView noms = new TextView(getContext());
        noms.setText(nomsPlayers.get(id));
        ((TableRow) players).addView(noms);

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

    private void loadStatsMatch(ArrayList<Integer> stats, int idStats){
        Cursor cursor_stats = dataBaseSQLite.getStatistiquesById(idStats);
        if (cursor_stats.moveToFirst()){
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Id")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Nombre_Points_Gagnes")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Premieres_Balles")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Aces")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Doubles_Fautes")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Premieres_Balles_Gagnes")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Coups_Droits_Gagnants")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Nombre_de_Jeu_Gagnes")));
            stats.add(cursor_stats.getInt(cursor_stats.getColumnIndex("Fautes_Directs")));
        }
    }

}
