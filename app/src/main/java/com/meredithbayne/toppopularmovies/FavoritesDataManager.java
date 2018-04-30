package com.meredithbayne.toppopularmovies;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class FavoritesDataManager extends AsyncQueryHandler {
    private FavoritesListener listener;

    public FavoritesDataManager(ContentResolver cr, FavoritesListener listener) {
        super(cr);
        this.listener = listener;
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        listener.onQueryComplete(cursor);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        listener.onInsertComplete();
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        listener.onDeleteComplete();
    }

    interface FavoritesListener {
        void onQueryComplete(Cursor cursor);

        void onInsertComplete();

        void onDeleteComplete();
    }
}
