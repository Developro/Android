package com.studios.uio443.cluck.presentation.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import com.studios.uio443.cluck.presentation.mvp.MainActivityVP;
import com.studios.uio443.cluck.presentation.view.fragment.AboutFragment;
import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;
import com.studios.uio443.cluck.presentation.view.fragment.FeedbackFragment;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;
import com.studios.uio443.cluck.presentation.view.fragment.SettingsFragment;

/**
 * Created by zundarik
 */

public class MainActivityPresenter extends BasePresenter<UserHolder, MainActivityVP.View> implements
        MainActivityVP.Presenter,
        FragmentNavigation.Presenter {

    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        // Business logic is in the presenter
        //view().updateText(model.getText() + " " + count);
    }

    @Override
    public void bindView(@NonNull MainActivityVP.View view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (model == null && !isLoadingData) {
            loadData();
        }
    }

    private void loadData() {
        isLoadingData = true;
        new LoadDataTask().execute();
    }

    public void buttonClick(String nameButton) {
        switch (nameButton) {
            case "buttonArchitecture":
                //model.setText("hi");
                updateView();
                break;
            default:
                break;
        }
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        view().setFragment(fragment);
    }

    @Override
    public void selectDrawerNavItem(String nameItem) {
        switch (nameItem) {
            case "nav_main":
                view().setFragment(new MainFragment());
                break;
            case "nav_settings":
                //view().setFragment(new SettingsFragment());
                //view().startActivity(SettingsActivity.class);
                break;
            case "nav_about":
                view().setFragment(new AboutFragment());
                break;
            case "nav_feedback":
                view().setFragment(new FeedbackFragment());
                break;
            default:
                break;
        }
    }

    // It's OK for this class not to be static and to keep a reference to the Presenter, as this
    // is retained during orientation changes and is lightweight (has no activity/view reference)
    @SuppressLint("StaticFieldLeak")
    private class LoadDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //SystemClock.sleep(3000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //setModel(new MainModel());
            isLoadingData = false;
        }
    }
}
