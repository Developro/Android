package com.studios.uio443.cluck.domain.interactor;
//
// Created by Korotchenko Evgeny on 28.07.2018.
//

import com.fernandocejas.arrow.checks.Preconditions;
import com.studios.uio443.cluck.domain.User;
import com.studios.uio443.cluck.domain.executor.PostExecutionThread;
import com.studios.uio443.cluck.domain.executor.ThreadExecutor;
import com.studios.uio443.cluck.domain.repository.UserRepository;

import io.reactivex.Observable;

public class GetUserProfile  extends UseCase<User, GetUserProfile.Params>  {

    private final UserRepository userRepository;

    GetUserProfile(UserRepository userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<User> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.userProfile(params.userId);
    }

    public static final class Params {

        private final int userId;

        private Params(int userId) {
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}
