package com.studios.uio443.cluck.data.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.util.Consts;
import com.studios.uio443.cluck.domain.User;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.studios.uio443.cluck.data.retrofit.CluckyAPI.BASE_URL;

/**
 * Created by zundarik
 */

public class GetUser {
    private static volatile GetUser instance;
    private String API_KEY = "cf8546gh5678jbd6182a837f232c43";
    private Retrofit client;
    private UserEntity user = null;

    public GetUser() {
        Log.d(Consts.TAG, "GetUser constructor");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();

        client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    public synchronized static GetUser getInstance() {
        Log.d(Consts.TAG, "GetUser.getInstance");
        if (instance == null)
            instance = new GetUser();
        return instance;
    }

    public UserEntity getUser() {
        return user;
    }

    public Observable<User> getUserById(int userId) {
        CluckyAPI service = client.create(CluckyAPI.class);

        return service.getUserRx(userId, API_KEY);
    }

    public Observable<UserEntity> auth(RequestBody requestBody) {
        CluckyAPI service = client.create(CluckyAPI.class);

        return service.auth(requestBody);
    }

}
