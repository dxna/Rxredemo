package com.example.administrator.rxredemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieListAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

     private ListView listview;
    private List<MovieEntity.SubjectsEntity>  movieEntityList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=(ListView)findViewById(R.id.listview);
        getMovie();
    }




    private void getMovie(){
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GetPictureService movieService = retrofit.create(GetPictureService.class);
        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<MovieEntity, List<MovieEntity.SubjectsEntity>>() {
                    @Override
                    public List<MovieEntity.SubjectsEntity> call(MovieEntity movieEntity) {
                        return movieEntity.getSubjects();
                    }
                }).subscribe(new Subscriber<List<MovieEntity.SubjectsEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<MovieEntity.SubjectsEntity> subjectsEntities) {
                movieEntityList.addAll(subjectsEntities);

                MovieListAdapter adapter=new MovieListAdapter(movieEntityList,getApplicationContext());
                listview.setAdapter(adapter);
                Log.e("大明",String.valueOf(movieEntityList.size()));
            }
        });

    }


}
