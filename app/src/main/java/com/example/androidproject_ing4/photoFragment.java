package com.example.androidproject_ing4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class photoFragment extends Fragment {
    private int[] images = new int[]{
            R.drawable.tennis1,
            R.drawable.tennis2,
            R.drawable.tennis3,
            R.drawable.tennis4,
            R.drawable.tennis5
    };

    private String[] imagesTitle = new String[]{
            "Tennis1",
            "Tennis2",
            "Tennis3",
            "Tennis4",
            "Tennis5"
    };

    public photoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_photo, container, false);

        CarouselView carouselView = RootView.findViewById(R.id.caroussel);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(images[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),imagesTitle[position], Toast.LENGTH_SHORT ).show();
            }
        });
        // Inflate the layout for this fragment
        return RootView;
    }
}
