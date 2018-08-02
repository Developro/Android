package com.studios.uio443.cluck.data.retrofit;
//
// Created by Korotchenko Evgeny on 02.08.2018.
//

import android.content.Context;

import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.entity.mapper.UserEntityJsonMapper;
import com.studios.uio443.cluck.domain.User;

import io.reactivex.Observable;
import retrofit2.Call;

public class CluckyApiImpl implements CluckyAPI {

    private final Context context;
    private final UserEntityJsonMapper userEntityJsonMapper;

    public CluckyApiImpl(Context context, UserEntityJsonMapper userEntityJsonMapper) {
        if (context == null || userEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context;
        this.userEntityJsonMapper = userEntityJsonMapper;
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
    public Observable<UserEntity> auth(String login, String password) {
        return null;
    }
}
