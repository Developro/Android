package com.studios.uio443.cluck.data.retrofit;

import com.studios.uio443.cluck.data.entity.UserEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CluckyAPI {
    //TODO здесь всё временно пока не заработает Api на сервере
    String BASE_URL = "http://185.244.173.142";

    @GET("/api/users")
    Observable<UserEntity> getUser(@Query("id") int id);

    @GET("/api/users")
    Observable<UserEntity> getCurrentUser();


    @POST("/api/auth/login")
    Observable<UserEntity> auth(@Body RequestBody requestBody);
}
