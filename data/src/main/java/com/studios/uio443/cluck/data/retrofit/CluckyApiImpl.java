package com.studios.uio443.cluck.data.retrofit;
//
// Created by Korotchenko Evgeny on 02.08.2018.
//

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.exception.NetworkConnectionException;
import com.studios.uio443.cluck.domain.User;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;

public class CluckyApiImpl implements CluckyAPI {

    private final Context context;

    public CluckyApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context;
    }

    @Override
    public Call<UserEntity> getUser(int id, String key) {
        return null;
    }

    @Override
    public Observable<User> getUserRx(int id, String key) {
        return null;
    }

    @Override
    public Observable<UserEntity> auth(RequestBody requestBody) {
        if (isThereInternetConnection()) {
            GetUser getUser = new GetUser();
            return getUser.auth(requestBody);
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
