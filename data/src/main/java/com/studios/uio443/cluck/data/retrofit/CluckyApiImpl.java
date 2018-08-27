package com.studios.uio443.cluck.data.retrofit;
//
// Created by Korotchenko Evgeny on 02.08.2018.
//

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.exception.NetworkConnectionException;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class CluckyApiImpl implements CluckyAPI {

    private final Context context;

    public CluckyApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context;
    }

    @Override
    public Observable<UserEntity> getUser(int id) {
        if (isThereInternetConnection()) {
            return GetUser.getInstance().getUserById(id);
        } else {
            return Observable.error(new NetworkConnectionException());
        }
    }

    @Override
    public Observable<UserEntity> getCurrentUser(int user_id) {
        if (isThereInternetConnection()) {
            return GetUser.getInstance().getCurrentUser(user_id);
        } else {
            return Observable.error(new NetworkConnectionException());
        }
    }

    @Override
    public Observable<UserEntity> auth(RequestBody requestBody) {
        if (isThereInternetConnection()) {
            return GetUser.getInstance().auth(requestBody);
        } else {
            return Observable.error(new NetworkConnectionException());
        }
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

}
