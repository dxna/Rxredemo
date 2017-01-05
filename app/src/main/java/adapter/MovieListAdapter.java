package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.rxredemo.MovieEntity;
import com.example.administrator.rxredemo.R;

import java.util.List;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    :
 * Author     :dxn
 * Date       : 2017/1/5 0005 下午 5:25
 */

public class MovieListAdapter extends BaseAdapter {

    private List<MovieEntity.SubjectsEntity> movieEntityList;
    private LayoutInflater inflater;
    private Context context;
    public MovieListAdapter(List<MovieEntity.SubjectsEntity> movieEntityList,Context context) {
        this.movieEntityList = movieEntityList;
        this.inflater=LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public int getCount() {
        return movieEntityList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view=inflater.inflate(R.layout.item_movie_list,null);
        MovieEntity.SubjectsEntity movieEntity=movieEntityList.get(i);
        //在view视图中查找id为image_photo的控件
        ImageView image_photo= (ImageView) view.findViewById(R.id.iv_movie_picture);

        Glide.with(context).load(movieEntity.getImages().getMedium()).into(image_photo);
        return view;
    }
}
