package com.example.yask.newmoviesexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yask on 03/06/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> amovie) {
        super(context,0,amovie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Movie movie = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_item_layout, parent, false);
        }
        ImageView ivImagePoster = (ImageView)convertView.findViewById(R.id.ivPosterImage);
        Picasso.with(getContext()).load(movie.getImageUrl()).into(ivImagePoster);
        return convertView;
    }
}
