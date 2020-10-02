package com.example.moviestabbasic;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.moviestabbasic.adapter.ReviewsAdapter;
import com.example.moviestabbasic.adapter.YouTubeVideoAdapter;
import com.example.moviestabbasic.data.DatabaseHandler;
import com.example.moviestabbasic.model.MovieDetail;

import com.example.moviestabbasic.model.Review;
import com.example.moviestabbasic.model.YouTubeVideo;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.moviestabbasic.R.drawable.love_off;

public class MovieDetailActivity extends AppCompatActivity {
    private RequestQueue queue;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MovieDetail movieDetail;
    private ImageView poster;
    private TextView title, overview, releaseDate, rating;
    private FloatingActionButton fab;
    private RecyclerView recyclerView, reviewsRecyclerView;
    private ArrayList<YouTubeVideo> youTubeVideoArrayList;
    private ArrayList<Review> reviewArrayList;
    private DatabaseHandler databaseHandler;
    private boolean favAdded;


    public void checkIfFavourite() {
        if(databaseHandler.checkMovieAdded(movieDetail.getId())) {
            fab.setImageResource((R.drawable.love_on));
            favAdded = true;
        }
        else {
            fab.setImageResource(R.drawable.love_off);
            favAdded = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        databaseHandler = new DatabaseHandler(this);

        movieDetail = (MovieDetail) getIntent().getExtras().getSerializable("movie_detail");

        fab = findViewById(R.id.fab);

        checkIfFavourite();

        recyclerView = findViewById(R.id.videos_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        String URL= "https://api.themoviedb.org/3/movie/"+movieDetail.getId()+"?api_key=d02e867767b7a1ad6ab95ce52de92b2b&language=en-US&append_to_response=videos";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("videos");
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    youTubeVideoArrayList = new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object =jsonArray.getJSONObject(i);
                        youTubeVideoArrayList.add(new YouTubeVideo(object.getString("key")));
                    }

                    if(jsonArray.length() == 0) {
                        findViewById(R.id.no_trailers_title).setVisibility(View.VISIBLE);
                    }

                    YouTubeVideoAdapter youTubeVideoAdapter = new YouTubeVideoAdapter(youTubeVideoArrayList,getApplicationContext());
                    recyclerView.setAdapter(youTubeVideoAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", String.valueOf(error));
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        poster = findViewById(R.id.movieDetailPoster);
        title = findViewById(R.id.movie_title);
        overview = findViewById(R.id.movie_overview);
        releaseDate = findViewById(R.id.movie_releasedate);
        rating = findViewById(R.id.movie_rating);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favAdded) {
                    databaseHandler.deleteMovie(movieDetail);
                    fab.setImageResource(love_off);
                    favAdded = false;
                    Snackbar.make(view, "Removed from your favourites!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    databaseHandler.addMovie(movieDetail);
                    fab.setImageResource(R.drawable.love_on);
                    favAdded = true;
                    Snackbar.make(view, "Added to your favourites!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+movieDetail.getPoster_path()).into(poster);

        title.setText(movieDetail.getTitle());
        overview.setText(movieDetail.getOverview());
        releaseDate.setText(movieDetail.getRelease_date());
        rating.setText(String.valueOf(movieDetail.getVote_average()));


        reviewsRecyclerView = findViewById(R.id.reviews_recyclerview);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        String URL1 = "https://api.themoviedb.org/3/movie/"+movieDetail.getId()+"/reviews?api_key=d02e867767b7a1ad6ab95ce52de92b2b&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    reviewArrayList = new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Log.d("json",object.getString("author"));

                        Review review = new Review();
                        review.setAuthor(object.getString("author"));
                        review.setContent(object.getString("content"));

                        reviewArrayList.add(review);
                    }
                    if(jsonArray.length() == 0) {
                        findViewById(R.id.no_reviews_title).setVisibility(View.VISIBLE);
                    }

                    ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getApplicationContext(),reviewArrayList);
                    reviewsRecyclerView.setAdapter(reviewsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",String.valueOf(error));
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest1);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Movie Details");
                } else {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                }
            }
        });

    }

}
