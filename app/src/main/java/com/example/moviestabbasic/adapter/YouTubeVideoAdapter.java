package com.example.moviestabbasic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestabbasic.R;
import com.example.moviestabbasic.model.YouTubeVideo;

import java.util.List;

public class YouTubeVideoAdapter extends RecyclerView.Adapter<YouTubeVideoAdapter.ViewHolder> {
    List<YouTubeVideo> youTubeVideoList;
    Context context;

    public YouTubeVideoAdapter(List<YouTubeVideo> youTubeVideoList, Context context) {
        this.youTubeVideoList = youTubeVideoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.webView.loadData(youTubeVideoList.get(position).getVideoUrlWebView(),"text/html","utf-8");
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Trailer");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,youTubeVideoList.get(position).getVideoUrl());

                    Intent chooserIntent = Intent.createChooser(shareIntent, "Share using");
                    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(chooserIntent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return youTubeVideoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        ImageButton shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shareButton = itemView.findViewById(R.id.shareButton);

            webView = itemView.findViewById(R.id.videoWebView);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());

            WebSettings webSettings = webView.getSettings();
            webSettings.setDomStorageEnabled(true);

        }
    }
}
