package com.example.androidproject_ing4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class gameListViewAdapter extends RecyclerView.Adapter<gameListViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Integer> gamesId = new ArrayList<>();
    private ArrayList<String> gamesDate = new ArrayList<>();
    private ArrayList<String> adversaires = new ArrayList<>();
    private ArrayList<Bitmap> gamesPicture = new ArrayList<>();

    private Context context;
    public View v;

    public gameListViewAdapter(ArrayList<Integer> gamesId, ArrayList<String> gamesDate, ArrayList<String> adversaires, ArrayList<Bitmap> gamesPicture, Context context) {
        this.gamesId = gamesId;
        this.gamesDate = gamesDate;
        this.adversaires = adversaires;
        this.gamesPicture = gamesPicture;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamelist_listitem, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.adversaire.setText(adversaires.get(position));
        holder.gameDate.setText(gamesDate.get(position));
        holder.image.setImageBitmap(gamesPicture.get(position));
        //holder.image.setImageResource(gamesPicture.get(position));

        holder.listLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on : "+ gamesDate.get(position));

                Intent intent = new Intent();
                intent.setClass(v.getContext(), GameInfo.class);
                intent.putExtra("id", gamesId.get(position));
                v.getContext().startActivity(intent);

                Toast.makeText(context, adversaires.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return adversaires.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView gameDate;
        TextView adversaire;
        RelativeLayout listLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.playerImageList);
            gameDate = itemView.findViewById(R.id.list_date);
            adversaire = itemView.findViewById(R.id.adversaireNameList);
            listLayout = itemView.findViewById(R.id.gameListLayout);
        }
    }
}
