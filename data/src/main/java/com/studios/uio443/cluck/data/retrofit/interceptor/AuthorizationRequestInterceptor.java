package com.studios.uio443.cluck.data.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//
// Created by Korotchenko Evgeny on 10.08.2018.
//

public class AuthorizationRequestInterceptor implements Interceptor {


    private final String AUTHORIZATION = "x-access-token";
    private String token;

    public AuthorizationRequestInterceptor(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (originalRequest.header(AUTHORIZATION) != null
                || token == null
                || token.equals("")) {
            return chain.proceed(originalRequest);
        }

        Request authorizedRequest = originalRequest.newBuilder()
                .header(AUTHORIZATION, token)
                .method(originalRequest.method(), originalRequest.body())
                .build();
        return chain.proceed(authorizedRequest);
    }
}
