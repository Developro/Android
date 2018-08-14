package com.studios.uio443.cluck.domain.interactor;
//
// Created by Korotchenko Evgeny on 28.07.2018.
//

import com.fernandocejas.arrow.checks.Preconditions;
import com.studios.uio443.cluck.domain.User;
import com.studios.uio443.cluck.domain.executor.PostExecutionThread;
import com.studios.uio443.cluck.domain.executor.ThreadExecutor;
import com.studios.uio443.cluck.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetUserProfile  extends UseCase<User, GetUserProfile.Params>  {

    private final UserRepository userRepository;

    @Inject
    GetUserProfile(UserRepository userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<User> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        if (params.isAuth)
            return this.userRepository.auth(params.user, params.password);
        else {
            return this.userRepository.currentUserProfile(params.userId);
        }

    }

    // тут делаем фактически два вида параметров, для аутентификации один,
    // другой когда будем просто обновлять юзера
    public static final class Params {

        private int userId;
        private boolean isAuth = false;
        private String user;
        private String password;

        private Params(int userId) {
            this.userId = userId;
        }

        private Params(String user, String password) {
            this.isAuth = true;
            this.user = user;
            this.password = password;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }

        public static Params auth(String user, String password) {
            return new Params(user, password);
        }
    }
}
