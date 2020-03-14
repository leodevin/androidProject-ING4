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


/**
 * A simple {@link Fragment} subclass.
 */
public class photoFragment extends Fragment {

    // Database
    DataBaseSQLite dataBaseSQLite;

    ArrayList<String> imagesTitle = new ArrayList<>();
    ArrayList<Integer> images = new ArrayList<>();

    int idMatchSelected;

    public photoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_photo, container, false);

        idMatchSelected = getArguments().getInt("id");

        dataBaseSQLite = new DataBaseSQLite(getContext());

        loadPhotosDuMatch();

        for (int i=0; i<imagesTitle.size(); i++){
            images.add(getResources().getIdentifier("drawable/"+imagesTitle.get(i), "id", getActivity().getPackageName()));
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
                Toast.makeText(getContext(),imagesTitle.get(position), Toast.LENGTH_SHORT ).show();
            }
        });
        // Inflate the layout for this fragment
        return RootView;
    }

    public void loadPhotosDuMatch(){
        Cursor cursor_photos = dataBaseSQLite.getPhotosById(idMatchSelected);
        if (cursor_photos.moveToFirst()){
            for (int i=0; i<cursor_photos.getCount(); i++){
                imagesTitle.add(cursor_photos.getString(i));
            }
        }
    }
}
