package com.meredithbayne.toppopularmovies.api;

/**
 * Must implmenet callbacks for when response is retrieved or when user cancels
 */

public interface IMoviesApi<T> {
    void onCancel();

    void onResponse(T result);
}
