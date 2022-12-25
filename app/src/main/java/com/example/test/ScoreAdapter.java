package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private Context context;
    int singleData;
    ArrayList<Score> scoreArrayList;
    SQLiteDatabase SQLdb;

    public ScoreAdapter(Context context, int singleData, ArrayList<Score> scoreArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.scoreArrayList = scoreArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_top_design, null);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {

        final Score score = scoreArrayList.get(position);

        holder.name.setText(score.getName());
        holder.score.setText(String.valueOf(score.getScore()));
    }

    @Override
    public int getItemCount() {
        return scoreArrayList.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView name, score;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Name);
            score = itemView.findViewById(R.id.Score);
        }
    }
}
