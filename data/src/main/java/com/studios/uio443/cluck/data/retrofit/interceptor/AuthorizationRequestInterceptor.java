package com.studios.uio443.cluck.data.retrofit.interceptor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.studios.uio443.cluck.data.util.JWTUtils;
import com.studios.uio443.cluck.data.util.SharedPreferencesUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.studios.uio443.cluck.data.retrofit.CluckyAPI.BASE_URL;
import static com.studios.uio443.cluck.data.util.Consts.EXP_DATE_TAG;
import static com.studios.uio443.cluck.data.util.Consts.REFRESH_TOKEN_TAG;

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

        // проверяем exp date
        long expDate = SharedPreferencesUtil.getProperty(EXP_DATE_TAG, 0L);
        if (expDate != 0) {
            Calendar c = Calendar.getInstance();
            Date nowDate = c.getTime();

            c.setTimeInMillis(expDate);
            Date expireDate = c.getTime();

            int result = nowDate.compareTo(expireDate);
            // по идее refresh_token никогда не понадобится в текущих условиях, но заготовку сделал
            if (result != -1) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("grant_type", "refresh_token");
                jsonObject.addProperty("refresh_token", SharedPreferencesUtil.getPropertyWithDecrypt(REFRESH_TOKEN_TAG, ""));

                Request refreshRequest = new Request.Builder()
                        .url(BASE_URL + "/api/auth/login")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))
                        .build();

                Response response = chain.proceed(refreshRequest);
                if (response.code() == 200 && response.body() != null) {
                    // тут получаем новые параметры
                    jsonObject = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    String refreshToken = jsonObject.get("refresh_token").getAsString();
                    String accessToken = jsonObject.get("access_token").getAsString();
                    if (!refreshToken.isEmpty()) {
                        SharedPreferencesUtil.savePropertyWithEncrypt(REFRESH_TOKEN_TAG, refreshToken);
                    }
                    if (!accessToken.isEmpty()) {
                        try {
                            expDate = JWTUtils.getIntParamFromJWTBody(accessToken, "exp");
                            SharedPreferencesUtil.saveProperty(EXP_DATE_TAG, expDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        token = accessToken;
                    }
                }
            }

        }

        Request authorizedRequest = originalRequest.newBuilder()
                .header(AUTHORIZATION, token)
                .method(originalRequest.method(), originalRequest.body())
                .build();

        return chain.proceed(authorizedRequest);
    }
}
