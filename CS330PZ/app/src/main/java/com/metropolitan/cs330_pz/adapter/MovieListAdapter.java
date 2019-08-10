package com.metropolitan.cs330_pz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metropolitan.cs330_pz.R;
import com.metropolitan.cs330_pz.entity.Movie;

import java.util.ArrayList;

public class MovieListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Movie> movieList;

    public MovieListAdapter(Context context, int layout, ArrayList<Movie> movieList) {
        this.context = context;
        this.layout = layout;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtGenre, txtDescription, txtRating;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtName = row.findViewById(R.id.txtName);
            holder.txtGenre = row.findViewById(R.id.txtGenre);
            holder.txtDescription = row.findViewById(R.id.txtDescription);
            holder.txtRating = row.findViewById(R.id.txtRating);
            holder.imageView = row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        Movie movie = movieList.get(i);

        holder.txtName.setText(movie.getName());
        holder.txtGenre.setText(movie.getGenre());
        holder.txtDescription.setText(movie.getDescription());
        holder.txtRating.setText(String.valueOf(movie.getRating()));

        byte[] movieImg = movie.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(movieImg, 0, movieImg.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

}