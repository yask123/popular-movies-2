package com.example.yask.newmoviesexplorer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yask on 03/06/16.
 */

public class Movie implements Serializable {

    public String title;
    public String overview;
    public String ID;
    public String release_date;
    public String stars;
    public String reviews;
    public String image;

    public static Movie fromJson(JSONObject jsonObject){
        Movie b = new Movie();
        try {
            b.ID = String.valueOf(jsonObject.getInt("id"));
            b.overview = jsonObject.getString("overview");
            b.title = jsonObject.getString("original_title");
            b.release_date = jsonObject.getString("release_date");
            b.stars = String.valueOf(jsonObject.getDouble("vote_average"));
            b.image = jsonObject.getString("poster_path");
        }
        catch (Exception e){
            e.printStackTrace();
            return null ;
        }
        return b;
    }

    public  static ArrayList<Movie> fromJson(JSONArray jsonArray){
        ArrayList<Movie> movies = new ArrayList<Movie>(jsonArray.length());

        for(int i=0;i<jsonArray.length();i++){
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
            Movie current_movie = fromJson(moviesJson);
            if (current_movie != null){
                movies.add(current_movie);
            }
        }
        return movies;
    }

    public String getImageUrl(){
        return "http://image.tmdb.org/t/p/w185/"+image;
    }

    public String getTrailerUrl(){
        return  "http://api.themoviedb.org/3/"+ID+"/videos";
    }

}
