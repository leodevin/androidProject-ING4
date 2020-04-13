package com.example.androidproject_ing4;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject_ing4.outils.DataBaseSQLite;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class locationFragment extends Fragment implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String TAG = "MapFragment";

    private DataBaseSQLite dataBaseSQLite;

    private int idMatchSelected;
    private ArrayList<Double> coordonnees = new ArrayList<>();

    public locationFragment() {
        // Required empty public constructor
    }

    private MapView mMapView;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        try {
            idMatchSelected = Objects.requireNonNull(getArguments()).getInt("id");
        }catch (NullPointerException e){ Log.d(TAG, "id match actuel -> "+idMatchSelected); }

        dataBaseSQLite = new DataBaseSQLite(getContext());

        coordonnees = loadCoordonneeMatch();
        Log.d(TAG, "Coordonnees - Lat: " + coordonnees.get(0) + " | Long: " +coordonnees.get(1));

        mMapView = (MapView) view.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);
        return view;
    }

    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap Googlemap) {
        map = Googlemap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
        //map.setMyLocationEnabled(true);

        float zoomLevel = 13.0f; //This goes up to 21
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(coordonnees.get(0), coordonnees.get(1)),zoomLevel));
        map.addMarker(new MarkerOptions().position(new LatLng(coordonnees.get(0), coordonnees.get(1))).title("Marker"));
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public ArrayList<Double> loadCoordonneeMatch(){
        int idLocalisation = 0;

        Cursor cursor_idLocalisation = dataBaseSQLite.getMatchLocalisationById(idMatchSelected);
        if (cursor_idLocalisation.moveToFirst()){
            if (!cursor_idLocalisation.isNull(0))
                idLocalisation = cursor_idLocalisation.getInt(0);
        }

        return dataBaseSQLite.getLocalisationById(idLocalisation);
    }
}
