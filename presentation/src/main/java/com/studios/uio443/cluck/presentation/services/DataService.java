package com.studios.uio443.cluck.presentation.services;

import org.json.JSONObject;

public class DataService {
    //TODO заменить на retrofit
    /**
     * Метод для отладки RestService
     * @return String
     */
    //TODO: надо вызывать в отдельном потоке, иначе будет исключение NetworkOnMainThreadException
    public static String testRest() {
        RestService rs = new RestService();

        RestService.Request r = new RestService.Request();
        r.method = RestService.METHOD_POST;
        r.url = "http://185.244.173.142/api/auth/onLogin";
        r.headers.put("Content-Type", "application/json");
        r.body = "{\"onLogin\":\"qwerty\", \"password\":\"qwerty\"}";

        JSONObject o;

        try {
            o = rs.request(r);
            return o.toString();
        } catch (Exception e) {
            e.getMessage();
            //Log.e("Some errors: ", e.getMessage());
        }

        return null;
    }
}
