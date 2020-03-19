package com.example.androidproject_ing4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidproject_ing4.outils.DataBaseSQLite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class addGameActivity extends AppCompatActivity {

    // Database
    DataBaseSQLite dataBaseSQLite;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001 ;

    //Variable pour l'enregistrement et le fontionnement
    private InternalMemoryController internalMemoryController;
    private Bitmap image_bitmap;
    private Uri image_uri;
    private ImageView takenPicture;
    private Button save;

    //Edit text de saisie des infos
    private EditText PlayerS1;
    private EditText PlayerS2;
    private EditText PlayerS3;
    private EditText PlayerS4;
    private EditText PlayerS5;

    private EditText AdversaireS1;
    private EditText AdversaireS2;
    private EditText AdversaireS3;
    private EditText AdversaireS4;
    private EditText AdversaireS5;

    //variable pour la saisie des stats
    private EditText Player_nb_pts_gagne_player;
    private EditText Player_nb_prem_ere_balle_player;
    private EditText Player_nb_aces_player;
    private EditText Player_nb_double_fautes_player;
    private EditText Player_nb_pts_gagne_prem_balle_player;
    private EditText Player_nb_coup_droit_gagant_player;
    private EditText Player_nb_jeu_gagne_player;
    private EditText Player_nb_faute_direct_player;

    private EditText Adversaire_nb_pts_gagne_player;
    private EditText Adversaire_nb_prem_ere_balle_player;
    private EditText Adversaire_nb_aces_player;
    private EditText Adversaire_nb_double_fautes_player;
    private EditText Adversaire_nb_pts_gagne_prem_balle_player;
    private EditText Adversaire_nb_coup_droit_gagant_player;
    private EditText Adversaire_nb_jeu_gagne_player;
    private EditText Adversaire_nb_faute_direct_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        dataBaseSQLite = new DataBaseSQLite(this);

        init();

        takenPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si l'os est mashmalow -> request runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                            //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permission
                        requestPermissions(permission,PERMISSION_CODE);
                    }else{
                        //permission already granted
                        openCamera();
                    }
                }else{
                    //system os < marshmalow
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enregistrer dans la database le nouveau match
                saveInDB();
            }
        });
    }

    private void saveInDB() {
        dataBaseSQLite.addSets(21,
                Integer.parseInt(String.valueOf(PlayerS1.getText())),
                Integer.parseInt(String.valueOf(PlayerS2.getText())),
                Integer.parseInt(String.valueOf(PlayerS3.getText())),
                Integer.parseInt(String.valueOf(PlayerS4.getText())),
                Integer.parseInt(String.valueOf(PlayerS5.getText())));
        dataBaseSQLite.addSets(31,
                Integer.parseInt(String.valueOf(AdversaireS1.getText())),
                Integer.parseInt(String.valueOf(AdversaireS2.getText())),
                Integer.parseInt(String.valueOf(AdversaireS3.getText())),
                Integer.parseInt(String.valueOf(AdversaireS4.getText())),
                Integer.parseInt(String.valueOf(AdversaireS5.getText())));

        dataBaseSQLite.addStatistiques(41,
                Integer.parseInt(String.valueOf(Player_nb_pts_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_prem_ere_balle_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_aces_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_double_fautes_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_pts_gagne_prem_balle_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_coup_droit_gagant_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_jeu_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_faute_direct_player.getText())));
        dataBaseSQLite.addStatistiques(51,
                Integer.parseInt(String.valueOf(Adversaire_nb_pts_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_prem_ere_balle_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_aces_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_double_fautes_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_pts_gagne_prem_balle_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_coup_droit_gagant_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_jeu_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_faute_direct_player.getText())));
    }

    private void init() {
        //Variable de fontionnement
        internalMemoryController = new InternalMemoryController();
        takenPicture = findViewById(R.id.apercu_picture);
        save = findViewById(R.id.AddGameSaveButton);

        //Edit text pour la saisie des infos sur le match
        PlayerS1 = findViewById(R.id.AGPlayerS1);
        PlayerS2 = findViewById(R.id.AGPlayerS2);
        PlayerS3 = findViewById(R.id.AGPlayerS3);
        PlayerS4 = findViewById(R.id.AGPlayerS4);
        PlayerS5 = findViewById(R.id.AGPlayerS5);

        AdversaireS1 = findViewById(R.id.AGAdversaireS1);
        AdversaireS2 = findViewById(R.id.AGAdversaireS2);
        AdversaireS3 = findViewById(R.id.AGAdversaireS3);
        AdversaireS4 = findViewById(R.id.AGAdversaireS4);
        AdversaireS5 = findViewById(R.id.AGAdversaireS5);

        Player_nb_aces_player = findViewById(R.id.AgPlayer_nb_aces_player);
        Player_nb_coup_droit_gagant_player = findViewById(R.id.AgPlayer_nb_coup_droit_gagant_player);
        Player_nb_double_fautes_player = findViewById(R.id.AgPlayer_nb_double_fautes_player);
        Player_nb_faute_direct_player = findViewById(R.id.AgPlayer_nb_faute_direct_player);
        Player_nb_jeu_gagne_player = findViewById(R.id.AgPlayer_nb_jeu_gagne_player);
        Player_nb_prem_ere_balle_player = findViewById(R.id.AgPlayer_nb_prem_ere_balle_player);
        Player_nb_pts_gagne_player = findViewById(R.id.AgPlayer_nb_pts_gagne_player);
        Player_nb_pts_gagne_prem_balle_player = findViewById(R.id.AgPlayer_nb_pts_gagne_prem_balle_player);

        Adversaire_nb_aces_player = findViewById(R.id.AgAdversaire_nb_aces_adversaire);
        Adversaire_nb_coup_droit_gagant_player = findViewById(R.id.AgAdversaire_nb_coup_droit_gagant_adversaire);
        Adversaire_nb_double_fautes_player = findViewById(R.id.AgAdversaire_nb_double_fautes_adversaire);
        Adversaire_nb_faute_direct_player = findViewById(R.id.AgAdversaire_nb_faute_direct_adversaire);
        Adversaire_nb_jeu_gagne_player = findViewById(R.id.AgAdversaire_nb_jeu_gagne_adversaire);
        Adversaire_nb_prem_ere_balle_player = findViewById(R.id.AgAdversaire_nb_prem_ere_balle_adversaire);
        Adversaire_nb_pts_gagne_player = findViewById(R.id.AgAdversaire_nb_pts_gagne_adversaire);
        Adversaire_nb_pts_gagne_prem_balle_player = findViewById(R.id.AgAdversaire_nb_pts_gagne_prem_balle_adversaire);
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //this method is called when a user presses allow  or deny permission request popup
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            //Do something with our captured image
            try {
                image_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            //on sauvegarde l'image
            internalMemoryController.writeImage(getApplicationContext(), image_bitmap);
            //on affiche l'image sauvegardé (pour vérifier que la sauvegarde a été correctement effectué
            takenPicture.setImageBitmap(internalMemoryController.readImage(getApplicationContext(),"images.txt"));
    }



}
