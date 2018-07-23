package com.studios.uio443.cluck.presentation.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.studios.uio443.cluck.presentation.services.DataService;
import com.studios.uio443.cluck.presentation.util.Consts;

public class UserHolder {

    @Nullable
    private User user;

    private static volatile UserHolder instance;

    private UserHolder() {
    }

    /**
     * Метод для получения ссылки на единственный объект
     *
     * @return UserHolder
     */
    public synchronized static UserHolder getInstance() {
        if (instance == null)
            instance = new UserHolder();
        return instance;
    }

    /**
     * Геттер сущности пользователя
     *
     * @return User
     */
    @Nullable
    public User getUser() {
        Log.d(Consts.TAG, "UserHolder.getUser");
        return user;
    }

    private void setUser(User user) {
        Log.d(Consts.TAG, "UserHolder.setUser");
        this.user = user;
    }

    private void addUser(User newUser) {
        Log.d(Consts.TAG, "UserHolder.addUser");
        if (newUser == null) return;
        //TODO signup - запрос к серверу на добавление аккаунта юзера
    }

    private void deleteUser(int index) {
        Log.d(Consts.TAG, "UserHolder.deleteUser");
        //TODO запрос к серверу на удаление аккаунта
    }

    /**
     * Метод для авторизации и получаения сущности пользователя
     *
     * @param login    логин
     * @param password пароль
     * @return User
     */
    public User authentication(String login, String password) {
        if (!login.equals("vasya@lol.com")) {
            return null;
        }
        //login - запрос к серверу
        //TODO заменить на retrofit
        //JSONObject
        String JSONObject = DataService.testRest();

        user = new User();

        user.setId(1);
        user.setLogin(login);
        user.setPassword(password);
        user.setAccessToken("");
        user.setRefreshToken("");

        return user;
    }

    /**
     * Метод для регистрации
     *
     * @param login    логин
     * @param password пароль
     * @param username имя пользователя
     * @return User
     */
    public User signup(String login, String password, String username) {
        if (!login.equals("vasya@lol.com")) {
            return null;
        }

        //signup - запрос к серверу
        //TODO заменить на retrofit
        //JSONObject
        String JSONObject = DataService.testRest();

        user = new User();

        user.setId(1);
        user.setLogin(login);
        user.setPassword(password);
        user.setAccessToken("");
        user.setRefreshToken("");

        return user;
    }
}
