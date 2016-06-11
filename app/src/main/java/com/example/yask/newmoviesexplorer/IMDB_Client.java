package com.example.yask.newmoviesexplorer;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by yask on 03/06/16.
 */

public class IMDB_Client {
    private  final String KEY = "05df3644ee3e75b407700ca976b07874";
    private final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
    private AsyncHttpClient client;

    public  IMDB_Client(){
        this.client = new AsyncHttpClient();
    }

    private String getURL(String relative_url){
        return BASE_URL+relative_url;
    }

    public void getMovies(JsonHttpResponseHandler handler){
        String url = getURL("/");
        RequestParams params = new RequestParams("api_key",KEY);
        Log.e("test",url);
        client.get("http://api.themoviedb.org/3/discover/movie/",params,handler);
    }

    public void getTrailer(String ID, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams("api_key",KEY);
        Log.e("URL","http://api.themoviedb.org/3/movie/"+ID+"/videos");
        client.get("http://api.themoviedb.org/3/movie/"+ID+"/videos",params,handler);

    }

    public void getReviews(String ID, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams("api_key",KEY);
        client.get("http://api.themoviedb.org/3/movie/"+ID+"/reviews",params,handler);
    }

}
