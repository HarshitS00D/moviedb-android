package com.example.moviestabbasic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.moviestabbasic.R;
import com.example.moviestabbasic.model.Review;

import org.w3c.dom.Text;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    Context context;
    List<Review> reviewList;

    public ReviewsAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.author_img.setText(reviewList.get(position).getAuthor().substring(0,1).toUpperCase());
        holder.author.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView author_img;
        TextView author;
        ReadMoreTextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author_img = itemView.findViewById(R.id.author_img);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }
    }
}
