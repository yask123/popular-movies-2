package com.example.yask.newmoviesexplorer;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

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

    public void getMovies(String selected_option, JsonHttpResponseHandler handler) {
        String url = getURL("/");
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("sort_by", selected_option);
        paramMap.put("api_key", KEY);
        RequestParams params = new RequestParams(paramMap);

        Log.e("test, url", "http://api.themoviedb.org/3/discover/movie/?api_key=" + KEY + "&sort_by=" + selected_option + ".desc");
        client.get("http://api.themoviedb.org/3/movie/" + selected_option + "?api_key=" + KEY, handler);
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
