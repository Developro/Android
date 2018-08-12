package com.studios.uio443.cluck.data.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.retrofit.interceptor.AuthorizationRequestInterceptor;
import com.studios.uio443.cluck.data.util.Consts;

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
    private CluckyAPI service;
    private String token = null;
    private AuthorizationRequestInterceptor authorizationRequestInterceptor;

    private GetUser() {
        Log.d(Consts.TAG, "GetUser constructor");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        authorizationRequestInterceptor = new AuthorizationRequestInterceptor(token);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(authorizationRequestInterceptor);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();

        client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        service = client.create(CluckyAPI.class);
    }

    public synchronized static GetUser getInstance() {
        Log.d(Consts.TAG, "GetUser.getInstance");
        if (instance == null)
            instance = new GetUser();
        return instance;
    }

    public void setToken(String newToken) {
        token = newToken;
        authorizationRequestInterceptor.setToken(newToken);
    }

    public boolean tokenIsEmpty() {
        return (token == null || token.equals(""));
    }

    public UserEntity getUser() {
        return user;
    }

    public Observable<UserEntity> getUserById(int userId) {
        return service.getUser(userId);
    }

    public Observable<UserEntity> getCurrentUser() {
        return service.getCurrentUser();
    }

    public Observable<UserEntity> auth(RequestBody requestBody) {
        return service.auth(requestBody);
    }

}
