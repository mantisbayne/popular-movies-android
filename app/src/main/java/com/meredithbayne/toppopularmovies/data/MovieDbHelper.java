package com.meredithbayne.toppopularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.meredithbayne.toppopularmovies.data.MovieContract.MovieEntry;

/**
 * DB Helper for Movie Entries
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    MovieDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TMDB_ID + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
                MovieEntry.COLUMN_POSTER_IMAGE + " BLOB, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieEntry.COLUMN_FAVORITE + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
