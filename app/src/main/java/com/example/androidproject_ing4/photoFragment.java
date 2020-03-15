package com.example.androidproject_ing4;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidproject_ing4.outils.DataBaseSQLite;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class photoFragment extends Fragment {

    private static final String TAG = "PhotoFragment";

    // Database
    private DataBaseSQLite dataBaseSQLite;

    private ArrayList<String> imagesTitle = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();

    private int idMatchSelected;

    public photoFragment() {// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_photo, container, false);

        try {
            idMatchSelected = Objects.requireNonNull(getArguments()).getInt("id");
        }catch (NullPointerException e){ Log.d(TAG, "id match actuel -> "+idMatchSelected); }

        dataBaseSQLite = new DataBaseSQLite(getContext());

        loadPhotosDuMatch();

        for (int i=0; i<imagesTitle.size(); i++){
            try {
                images.add(getResources().getIdentifier("drawable/"+imagesTitle.get(i), "id", Objects.requireNonNull(getActivity()).getPackageName()));
            }catch (NullPointerException e){ Log.d(TAG, "Problem Package name");}
        }

        CarouselView carouselView = RootView.findViewById(R.id.caroussel);
        carouselView.setPageCount(images.size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(images.get(position));
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),imagesTitle.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return RootView;
    }

    private void loadPhotosDuMatch(){
        Cursor cursor_photos = dataBaseSQLite.getPhotosById(idMatchSelected);
        if (cursor_photos.moveToFirst()){
            if (cursor_photos.moveToFirst()){
                do{
                    if (!cursor_photos.isNull(0)){
                        imagesTitle.add(cursor_photos.getString(0));
                    } else {
                        Log.d(TAG, "Plus de photo pour le match selectionné ");
                    }
                }while (cursor_photos.moveToNext());
            }
        }
    }
}
