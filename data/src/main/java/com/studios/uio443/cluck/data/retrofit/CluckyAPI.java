package com.studios.uio443.cluck.data.retrofit;

import com.studios.uio443.cluck.data.entity.UserEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CluckyAPI {
    //TODO здесь всё временно пока не заработает Api на сервере

    @GET("/data/user")
    Call<UserEntity> getUser(@Query("id") int id, @Query("APPID") String key);


}
