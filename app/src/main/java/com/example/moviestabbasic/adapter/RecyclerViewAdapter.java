package com.example.moviestabbasic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviestabbasic.MovieDetailActivity;
import com.example.moviestabbasic.R;
import com.example.moviestabbasic.model.MovieDetail;

import java.io.Serializable;
import java.util.List;

import com.example.moviestabbasic.model.MovieDetail;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<MovieDetail> movieList;

    public RecyclerViewAdapter(Context context, List<MovieDetail> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MovieDetail movieDetail = movieList.get(position);

            ImageView poster = holder.poster;
            TextView title = holder.title;
            TextView rating = holder.rating;

            title.setText(movieDetail.getTitle());
            rating.setText(movieDetail.getVote_average().toString());

            Glide.with(context).load("https://image.tmdb.org/t/p/w500"+movieDetail.getPoster_path()).into(poster);

    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView poster;
        public TextView title;
        public TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            MovieDetail movieDetail = movieList.get(position);

            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movie_detail", movieDetail);
            context.startActivity(intent);

        }
    }
}
