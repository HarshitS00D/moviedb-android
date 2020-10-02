package com.example.moviestabbasic;

import android.database.sqlite.SQLiteDatabase;
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

import com.example.moviestabbasic.adapter.RecyclerViewAdapter;
import com.example.moviestabbasic.data.DatabaseHandler;
import com.example.moviestabbasic.model.MovieDetail;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavourite extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<MovieDetail> movieDetailArrayList;
    private DatabaseHandler databaseHandler;

    public FragmentFavourite() {
    }

    public void notifyDataChanged() {
        movieDetailArrayList.clear();
        movieDetailArrayList.addAll(databaseHandler.getAllMovies());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyDataChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_fragment,container,false);

        recyclerView = view.findViewById(R.id.favourite_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        databaseHandler = new DatabaseHandler(getContext());
//
//        SQLiteDatabase db = databaseHandler.getWritableDatabase(); // To Drop Table
//        databaseHandler.onUpgrade(db,1,2);

        movieDetailArrayList = databaseHandler.getAllMovies();

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),movieDetailArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}
