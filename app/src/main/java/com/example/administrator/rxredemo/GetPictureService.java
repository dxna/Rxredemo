package com.example.administrator.rxredemo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 球友圈
 * Author     :dxn
 * Date       : 2017/1/4 0004 下午 2:55
 */

public interface GetPictureService {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

}
