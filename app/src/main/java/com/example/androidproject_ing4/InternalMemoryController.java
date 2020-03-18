package com.example.androidproject_ing4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalMemoryController extends AppCompatActivity {

    public InternalMemoryController() {

    }

    public void writeImage (Context context, Bitmap image_bitmap){
        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = context.openFileOutput("images.txt", MODE_PRIVATE);
            image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap readImage(Context context, String imagePath){
        Bitmap imageLoad = null;
        try {
            File filePath = context.getFileStreamPath(imagePath);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            return imageLoad = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageLoad;
    }
}
