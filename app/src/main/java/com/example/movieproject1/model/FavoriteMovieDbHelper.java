package com.example.movieproject1.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movieproject1.model.FavoriteMovieContract.FavoriteMovieEntry;

public class FavoriteMovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favorite_movie.db";
    public static final int DATABASE_VERSION = 3;

    public FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_MOVIE_TABLE =

                "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " (" +

                        /*
                         * WeatherEntry did not explicitly declare a column called "_ID". However,
                         * WeatherEntry implements the interface, "BaseColumns", which does have a field
                         * named "_ID". We use that here to designate our table's primary key.
                         */
                        FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        FavoriteMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +

                        FavoriteMovieEntry.COLUMN_MOVIE_TITLE + " INTEGER NOT NULL, " +
//TODO 1 String is a valid type here?
                        FavoriteMovieEntry.COLUMN_MOVIE_IMAGE + " STRING NOT NULL" + ");";

        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
