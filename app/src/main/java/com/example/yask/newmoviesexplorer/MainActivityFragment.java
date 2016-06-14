package com.example.yask.newmoviesexplorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String MOVIE_DETAIL_KEY = "movie";
    private static final long serialVersionUID = -8959832007991513854L;
    public GridView lvMovies;
    public MovieAdapter adapterMovie;
    View rootView;
    IMDB_Client client;


    public MainActivityFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));

        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        setupMovieSelectedListener();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order = prefs.getString("sort_order", "");
        Log.v("Selected settings = ", order);
        fetchMoviees(order);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        lvMovies = (GridView) rootView.findViewById(R.id.lvmovie);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovie = new MovieAdapter(getActivity(), aMovies);
        lvMovies.setAdapter(adapterMovie);



        return rootView;
    }

    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent i = new Intent(getActivity(), MovieDetail.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovie.getItem(position));
                startActivity(i);

            }


        });
    }

    public void fetchMoviees(String order) {
        adapterMovie.clear();
        adapterMovie.notifyDataSetChanged();
        if (order.equals("fav")) {
            adapterMovie.clear();
            adapterMovie.notifyDataSetChanged();
            List<User> users_movies = User.findWithQuery(User.class, "Select * from User");
            for (User each_movie : users_movies) {
                Movie temp = new Movie();
                temp.ID = each_movie.FAVID;
                temp.title = each_movie.favtitle;
                temp.overview = each_movie.favoverview;
                temp.image = each_movie.favimage;
                temp.release_date = each_movie.favrelease_date;
                temp.stars = each_movie.favstars;
                adapterMovie.add(temp);
            }
            adapterMovie.notifyDataSetChanged();
        } else {
            adapterMovie.clear();
            adapterMovie.notifyDataSetChanged();
            client = new IMDB_Client();
            client.getMovies(order, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                    JSONArray items = null;
                    try {
                        items = responseBody.getJSONArray("results");
                        ArrayList<Movie> movies = Movie.fromJson(items);


                        for (Movie each_movie : movies) {
                            adapterMovie.add(each_movie);
                        }
                        adapterMovie.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}
