package com.example.moviestabbasic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviestabbasic.adapter.RecyclerViewAdapter;
import com.example.moviestabbasic.model.MovieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTopRated extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<MovieDetail> movieDetailArrayList;
    private int pageCount;

    public FragmentTopRated() {
        pageCount = 1;
        movieDetailArrayList = new ArrayList<>();
    }

    public void updateMovieDetailArrayList() {
        String URL= "https://api.themoviedb.org/3/movie/top_rated?api_key=d02e867767b7a1ad6ab95ce52de92b2b&language=en-US&page="+pageCount;
        pageCount++;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject object =jsonArray.getJSONObject(i);
                                MovieDetail movieDetail = new MovieDetail();
                                movieDetail.setId(object.getInt("id"));
                                movieDetail.setTitle(object.getString("title"));
                                movieDetail.setPoster_path(object.getString("poster_path"));
                                movieDetail.setOverview(object.getString("overview"));
                                movieDetail.setRelease_date(object.getString("release_date"));
                                movieDetail.setVote_average(object.getDouble("vote_average"));

                                movieDetailArrayList.add(movieDetail);
                            }

                            recyclerViewAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("error", String.valueOf(error));

                    }
                });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleySingleton.getInstance(getContext()).getRequestQueue();
        updateMovieDetailArrayList();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.toprated_fragment,container,false);

        recyclerView = view.findViewById(R.id.toprated_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!recyclerView.canScrollVertically(1)) {
                    updateMovieDetailArrayList();
                }
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),movieDetailArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}
