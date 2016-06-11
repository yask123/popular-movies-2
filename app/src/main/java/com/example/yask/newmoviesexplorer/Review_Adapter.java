package com.example.yask.newmoviesexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yask on 04/06/16.
 */

public class Review_Adapter extends ArrayAdapter<Review>{
    public Review_Adapter(Context context, ArrayList<Review> areview) {
        super(context,0,areview);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_item, parent, false);
        }
        Review areview = getItem(position);

        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView author_review = (TextView) convertView.findViewById(R.id.author_review);

        author.setText(areview.author+" wrote: ");
        author_review.setText(areview.author_review);

        return convertView;
    }
}
