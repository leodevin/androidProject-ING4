package com.example.androidproject_ing4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidproject_ing4.outils.DataBaseSQLite;
import java.io.IOException;


public class addGameActivity extends AppCompatActivity {

    private static final String TAG = "AddGameActivity";
    // Database
    DataBaseSQLite dataBaseSQLite;
    int path;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001 ;

    //Variable pour l'enregistrement et le fontionnement
    private InternalMemoryController internalMemoryController;
    private Bitmap image_bitmap;
    private Uri image_uri;
    private ImageView takenPicture;
    private Button save;

    private EditText date;
    private EditText nomAdversaire;
    private EditText duree;

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

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        dataBaseSQLite = new DataBaseSQLite(this);
        path = dataBaseSQLite.getLastIdFromMatchs();

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
                if (checkNotNullText()){
                    saveInDB();
                    Intent intent = new Intent(addGameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(addGameActivity.this, "Veuillez remplir tous les champs !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        //Variable de fontionnement
        internalMemoryController = new InternalMemoryController();
        takenPicture = findViewById(R.id.apercu_picture);
        save = findViewById(R.id.AddGameSaveButton);

        date = findViewById(R.id.date_form_value);
        nomAdversaire = findViewById(R.id.adversaire_form_value);
        duree = findViewById(R.id.duree_form_value);

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

    private void saveInDB() {
        long id_localisation = dataBaseSQLite.addLocalisation(latitude, longitude);
        long id_set1 = dataBaseSQLite.addSets(Integer.parseInt(String.valueOf(PlayerS1.getText())),
                Integer.parseInt(String.valueOf(PlayerS2.getText())),
                Integer.parseInt(String.valueOf(PlayerS3.getText())),
                Integer.parseInt(String.valueOf(PlayerS4.getText())),
                Integer.parseInt(String.valueOf(PlayerS5.getText())));
        long id_set2 = dataBaseSQLite.addSets(Integer.parseInt(String.valueOf(AdversaireS1.getText())),
                Integer.parseInt(String.valueOf(AdversaireS2.getText())),
                Integer.parseInt(String.valueOf(AdversaireS3.getText())),
                Integer.parseInt(String.valueOf(AdversaireS4.getText())),
                Integer.parseInt(String.valueOf(AdversaireS5.getText())));
        long id_stats1 = dataBaseSQLite.addStatistiques(Integer.parseInt(String.valueOf(Player_nb_pts_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_prem_ere_balle_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_aces_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_double_fautes_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_pts_gagne_prem_balle_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_coup_droit_gagant_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_jeu_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Player_nb_faute_direct_player.getText())));
        long id_stats2 = dataBaseSQLite.addStatistiques(Integer.parseInt(String.valueOf(Adversaire_nb_pts_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_prem_ere_balle_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_aces_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_double_fautes_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_pts_gagne_prem_balle_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_coup_droit_gagant_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_jeu_gagne_player.getText())),
                Integer.parseInt(String.valueOf(Adversaire_nb_faute_direct_player.getText())));
        long id_match = dataBaseSQLite.addMatch(String.valueOf(date.getText()),
                "Roger Federrer",
                String.valueOf(nomAdversaire.getText()),
                String.valueOf(duree.getText()),
                (int) id_localisation,
                (int) id_set1,
                (int) id_set2,
                (int) id_stats1,
                (int) id_stats2);
        dataBaseSQLite.addPhoto(String.valueOf(path), (int) id_match);
    }

    private Boolean checkNotNullText(){
        if (TextUtils.isEmpty(date.getText().toString())
                || TextUtils.isEmpty(nomAdversaire.getText().toString())
                || TextUtils.isEmpty(duree.getText().toString())
                || TextUtils.isEmpty(PlayerS1.getText().toString())
                || TextUtils.isEmpty(PlayerS2.getText().toString())
                || TextUtils.isEmpty(PlayerS3.getText().toString())
                || TextUtils.isEmpty(PlayerS4.getText().toString())
                || TextUtils.isEmpty(PlayerS5.getText().toString())
                || TextUtils.isEmpty(AdversaireS1.getText().toString())
                || TextUtils.isEmpty(AdversaireS2.getText().toString())
                || TextUtils.isEmpty(AdversaireS3.getText().toString())
                || TextUtils.isEmpty(AdversaireS4.getText().toString())
                || TextUtils.isEmpty(AdversaireS5.getText().toString())
                || TextUtils.isEmpty(Player_nb_pts_gagne_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_prem_ere_balle_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_aces_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_double_fautes_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_pts_gagne_prem_balle_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_coup_droit_gagant_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_jeu_gagne_player.getText().toString())
                || TextUtils.isEmpty(Player_nb_faute_direct_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_pts_gagne_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_prem_ere_balle_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_aces_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_double_fautes_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_pts_gagne_prem_balle_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_coup_droit_gagant_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_jeu_gagne_player.getText().toString())
                || TextUtils.isEmpty(Adversaire_nb_faute_direct_player.getText().toString())){
            return false;
        } else{
            return true;
        }
    }


    /* ------------ POUR LE FONCTIONNEMENT DE LA CAMERA ------------*/
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
            internalMemoryController.writeImage(getApplicationContext(), image_bitmap, String.valueOf(path));
            //on affiche l'image sauvegardé (pour vérifier que la sauvegarde a été correctement effectué
            takenPicture.setImageBitmap(internalMemoryController.readImage(getApplicationContext(), String.valueOf(path)));
    }




    /* ------------ AVOIR LA LOCALISATION DE L'UTILISATEUR ------------ */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(addGameActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (addGameActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(addGameActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                latitude = location2.getLatitude();
                longitude = location2.getLongitude();

                Toast.makeText(this, "Your current location is"+ "\n" + "Latitude = " + latitude
                        + "\n" + "Longitude = " + longitude, Toast.LENGTH_LONG).show();

            } else  if (location1 != null) {
                latitude = location2.getLatitude();
                longitude = location2.getLongitude();

                Toast.makeText(this, "Your current location is"+ "\n" + "Latitude = " + latitude
                        + "\n" + "Longitude = " + longitude, Toast.LENGTH_LONG).show();


            } else  if (location2 != null) {
                latitude = location2.getLatitude();
                longitude = location2.getLongitude();

                Toast.makeText(this, "Your current location is"+ "\n" + "Latitude = " + latitude
                        + "\n" + "Longitude = " + longitude, Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
