package com.example.administrator.rxredemo;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
     private TextView iv;
    private ImageView iv_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=(TextView)findViewById(R.id.iv);
        OberableLog();
        getMovie();
    }

    private void OberableLog()
    {
        Subscriber<String> stringSubscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("小明的爷爷", "Item: " + "completed");
                iv.setText("小明的爷爷"+ "Item: " + "completed");
                System.out.print("completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("小明的爷爷", "Item: " + "error");
                System.out.print("error");
                iv.setText("小明的爷爷"+ "Item: " + "error");
            }

            @Override
            public void onNext(String s) {
                Log.d("小明的爷爷", "Item: " + s);
                System.out.print(s);
                iv.setText("小明的爷爷"+ "Item: " + s);
            }
        };
        rx.Observable observable= rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("小明的爷爷");
                subscriber.onNext("活了108岁");
                subscriber.onNext("为什么");
                subscriber.onCompleted();
            }


        });
        observable.subscribe(stringSubscriber);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });

    }

    private void  initPic()

    {
        final int drawableRes =R.mipmap.ic_launcher;
        iv_iv = (ImageView)findViewById(R.id.iv_iv);
        Observable observable= (Observable) Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }


        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                iv_iv.setImageDrawable(drawable);
            }
        });
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
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iv.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        iv.setText(movieEntity.toString());
                    }
                });
    }


}
