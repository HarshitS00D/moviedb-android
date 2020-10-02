package com.example.moviestabbasic.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.moviestabbasic.model.MovieDetail;
import com.example.moviestabbasic.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE "+Constants.TABLE_NAME +"("
                + Constants.KEY_ID +" INTEGER PRIMARY KEY,"
                + Constants.KEY_TITLE + " TEXT,"
                + Constants.KEY_POSTER_PATH + " TEXT,"
                + Constants.KEY_OVERVIEW + " TEXT,"
                + Constants.KEY_RELEASE_DATE + " TEXT,"
                + Constants.KEY_VOTE_AVERAGE + " REAL,"
                + Constants.KEY_TIMESTAMP_ADDED + " LONG);";

        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        Log.d("movie","table deleted");
        onCreate(db);
    }

    public  void addMovie(MovieDetail movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.KEY_ID,movie.getId());
        values.put(Constants.KEY_TITLE,movie.getTitle());
        values.put(Constants.KEY_POSTER_PATH,movie.getPoster_path());
        values.put(Constants.KEY_OVERVIEW,movie.getOverview());
        values.put(Constants.KEY_RELEASE_DATE,movie.getRelease_date());
        values.put(Constants.KEY_VOTE_AVERAGE,movie.getVote_average());
        values.put(Constants.KEY_TIMESTAMP_ADDED,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);
    }

    public void deleteMovie(MovieDetail movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME,Constants.KEY_ID +"=?",new String[]{String.valueOf(movie.getId())});
        db.close();

    }

    public List<MovieDetail> getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<MovieDetail> movieList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                            Constants.KEY_TITLE,
                            Constants.KEY_POSTER_PATH,
                            Constants.KEY_OVERVIEW,
                            Constants.KEY_RELEASE_DATE,
                            Constants.KEY_VOTE_AVERAGE,
                            Constants.KEY_TIMESTAMP_ADDED},
                null,null,null,null,Constants.KEY_TIMESTAMP_ADDED+" DESC"
                );

        if(cursor.moveToFirst()) {
            do {
                MovieDetail movieDetail = new MovieDetail();
                movieDetail.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                movieDetail.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
                movieDetail.setPoster_path(cursor.getString(cursor.getColumnIndex(Constants.KEY_POSTER_PATH)));
                movieDetail.setOverview(cursor.getString(cursor.getColumnIndex(Constants.KEY_OVERVIEW)));
                movieDetail.setRelease_date(cursor.getString(cursor.getColumnIndex(Constants.KEY_RELEASE_DATE)));
                movieDetail.setVote_average(cursor.getDouble(cursor.getColumnIndex(Constants.KEY_VOTE_AVERAGE)));

                movieList.add(movieDetail);

            } while(cursor.moveToNext());
        }

        return movieList;
    }

    public Boolean checkMovieAdded(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID},
                Constants.KEY_ID +"=?",
                new String[]{String.valueOf(id)},null,null,null
                );

        if(cursor.moveToFirst()) return true;
        else return false;
    }

}
