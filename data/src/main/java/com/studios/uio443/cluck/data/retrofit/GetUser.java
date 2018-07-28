package com.studios.uio443.cluck.data.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.util.Consts;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zundarik
 */

public class GetUser {
    private static volatile GetUser instance;
    private String BASE_URL = "http://api.185.244.173.142";
    private String API_KEY = "cf8546gh5678jbd6182a837f232c43";
    private Retrofit client;
    private UserEntity user = null;
    private OnGetUserListener listener = null;

    private GetUser() {
        Log.d(Consts.TAG, "GetUser constructor");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();

        client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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

    public void registerListener(Object o) {
        listener = (OnGetUserListener) o;
    }

    public UserEntity getUser() {
        return user;
    }

    public void getUserFromServer(int idUser) {
        Log.d(Consts.TAG, "GetUser.getWeatherByCoord: Run getWeatherByCoord method.");

        CluckyAPI service = client.create(CluckyAPI.class);

        Call<UserEntity> call = service.getUser(idUser, API_KEY);

        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    Log.d(Consts.TAG, "GetUser.getUserFromServer: USER RESPONSE ID OK!");
                    //Log.d(TAG,response.toString());
                    //Log.d(TAG,response.body().getUser().get(0).toString());
                    //Log.d(TAG,response.body().getName());
                    user = response.body();
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    user = null;
                    Log.d(Consts.TAG, "GetUser.getUserFromServer: USER RESPONSE IS BAD!");
                    if (response.body() != null) {
                        Log.d(Consts.TAG, Objects.requireNonNull(response.body()).toString());
                    }
                }

                listener.OnGetUserUpdate(user);
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {

                Log.d(Consts.TAG, "GetUser.getUserFromServer: USER ENQUEUE FAILURE!");
                if (t instanceof IOException) {
                    Log.d(Consts.TAG, "this is an actual network failure :( inform the user and possibly retry");
                } else {
                    Log.d(Consts.TAG, "error :(");
                }

                listener.OnGetUserClearAll();
            }
        });
    }

    public interface OnGetUserListener {
        void OnGetUserUpdate(UserEntity user);

        void OnGetUserClearAll();
    }
}
