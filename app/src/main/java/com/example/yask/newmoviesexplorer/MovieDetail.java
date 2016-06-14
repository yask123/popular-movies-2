package com.example.yask.newmoviesexplorer;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieDetail extends AppCompatActivity {
    Review_Adapter review_adapter;
    Movie current_movie;
    TextView video;
    ListView detail_review;
    Button fav;
    LinearLayout ll_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        current_movie = (Movie) getIntent().getSerializableExtra(MainActivityFragment.MOVIE_DETAIL_KEY);

        TextView dv_title = (TextView) findViewById(R.id.tvTitle);
        ImageView ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        TextView tvCriticsConsensus = (TextView) findViewById(R.id.overview);
        TextView ratings = (TextView) findViewById(R.id.ratings);

        //detail_review = (ListView) findViewById(R.id.detail_review);
        dv_title.setText(current_movie.title);
        tvCriticsConsensus.setText(current_movie.overview);
        ratings.setText(current_movie.stars);
        Picasso.with(this).load(current_movie.getImageUrl()).into(ivPosterImage);
        ArrayList<Review> temp = new ArrayList<Review>();
        review_adapter = new Review_Adapter(this,temp);
        //detail_review.setAdapter(review_adapter);
        fav = (Button)findViewById(R.id.fav);
        fetchVideos();

        fav_button(current_movie.ID);

        ll_detail = (LinearLayout)findViewById(R.id.ll_detail);

    }
    public void fetchVideos(){
        IMDB_Client client = new IMDB_Client();
        client.getTrailer(current_movie.ID, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray jsonarray;
                TextView trailer_heading = new TextView(getApplicationContext());
                trailer_heading.setText("Trailers");
                trailer_heading.setTextColor(Color.BLACK);
                trailer_heading.setTextSize(20);
                ll_detail.addView(trailer_heading);

                try {
                    jsonarray = response.getJSONArray("results");
                    //JSONObject item = jsonarray.getJSONObject(0);
                    //video.setText(item.getString("key"));
                    for(int i=0;i < jsonarray.length();i++){
                        JSONObject item = jsonarray.getJSONObject(i);
                        final Button new_trailer = new Button(getApplicationContext());
                        final String vid_id = item.getString("key");
                        new_trailer.setText("Trailer " + i + 1);
                        new_trailer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + vid_id)));
                            }
                        });
                        ll_detail.addView(new_trailer);
                    }
                    fetchReviws();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchReviws(){

        IMDB_Client client = new IMDB_Client();
        client.getReviews(current_movie.ID,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray jsonArray;
                TextView review_heading = new TextView(getApplicationContext());
                review_heading.setText("Reviews");
                review_heading.setTextColor(Color.BLACK);
                review_heading.setTextSize(20);
                ll_detail.addView(review_heading);
                try {
                    Log.e("Review",response.toString());
                    jsonArray = response.getJSONArray("results");
                    ArrayList<Review> reviews = Review.fromJson(jsonArray);

                    for (Review each_review : reviews){

                        Log.e("ytest",each_review.author_review);
                        TextView new_review = new TextView(getApplicationContext());
                        TextView new_author = new TextView(getApplicationContext());
                        new_author.setText(each_review.author+" wrote: ");
                        new_review.setText(each_review.author_review);
                        new_review.setTextColor(Color.BLACK);
                        new_author.setTextColor(Color.BLUE);
                        ll_detail.addView(new_author);
                        ll_detail.addView(new_review);
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }


        });
    }

    public void fav_button(final String ID){
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Note.find(Note.class, "name = ? and title = ?", "satya", "title1");
                //String ID,String title, String overview, String releasedate, String stars, String image){

                if (User.find(User.class,"favid = ?",ID).isEmpty()){
                    User new_fav = new User(current_movie.ID,current_movie.title,current_movie.overview,current_movie.release_date,current_movie.stars,current_movie.image);
                    new_fav.save();
                }
                else {
                    Log.e("DB","Movie already selected");
                    fav.setText("Starred");
                }
            }
        });
    }
}
