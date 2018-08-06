package com.studios.uio443.cluck.data.retrofit;

import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.domain.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CluckyAPI {
    //TODO здесь всё временно пока не заработает Api на сервере
    String BASE_URL = "http://185.244.173.142";

    @GET("/data/user")
    Call<UserEntity> getUser(@Query("id") int id, @Query("APPID") String key);

    @GET("/data/user")
    Observable<User> getUserRx(@Query("id") int id, @Query("APPID") String key);

    @POST("/api/auth/login")
    Observable<UserEntity> auth(@Query("login") String login, @Query("password") String password);
}
