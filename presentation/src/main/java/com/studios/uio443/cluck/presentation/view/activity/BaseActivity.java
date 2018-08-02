package com.studios.uio443.cluck.presentation.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;

import com.studios.uio443.cluck.presentation.navigation.Navigator;
import com.studios.uio443.cluck.presentation.util.Consts;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Base {@link android.support.v7.app.AppCompatActivity} class for every Activity in this application.
 *
 * Created by zundarik
 */

public abstract class BaseActivity extends DaggerAppCompatActivity {

  @Inject
  Navigator navigator;

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  protected void replaceFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.replace(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  public void startActivity(Class activityClass) {
    try {
      Intent intent = new Intent(this, activityClass);
      startActivity(intent);
    } catch (NullPointerException e) {
      Log.e(Consts.TAG, "BaseActivity.setStartActivity\n" + e.getMessage());
      e.printStackTrace();
    }
  }
}
