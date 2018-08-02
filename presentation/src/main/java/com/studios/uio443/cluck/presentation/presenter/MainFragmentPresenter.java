package com.studios.uio443.cluck.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.mvp.MainFragmentVP;
import com.studios.uio443.cluck.presentation.structure.facade.Facade;
import com.studios.uio443.cluck.presentation.structure.router.MainRouter;
import com.studios.uio443.cluck.presentation.util.Consts;

import javax.inject.Inject;

/**
 * Created by zundarik
 */

public class MainFragmentPresenter extends BasePresenter<UserHolder, MainFragmentVP.View>
        implements MainFragmentVP.Presenter {

    private MainFragmentVP.View view; //будет наш фрагмент, т.к. Мы на него забайндили
    private Facade facade; //@ActivityScope
    private MainRouter router; //@ActivityScope
    private int someId;

    @Inject
    public MainFragmentPresenter(MainFragmentVP.View view, Facade facade, MainRouter router) {
        this.view = view;
        this.facade = facade;
        this.router = router;
    }

    @Override
    public void doSometing() {
        view.onResult(getValue());
    }

    public String getValue() {
        return "Presenter is " + this + "\n" + "SomeId: " + someId + "\nView is " + view + "\n" + facade.getValue();
    }


    @Override
    protected void updateView() {
        Log.d(Consts.TAG, "NoteFragmentPresenter.updateView");
        // Business logic is in the presenter
    }

    @Override
    public void bindView(@NonNull MainFragmentVP.View view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (model == null) {
            setModel(UserHolder.getInstance());
        }
    }
}
