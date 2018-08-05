package com.studios.uio443.cluck.presentation.structure.router.impl;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Log;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.view.activity.BaseActivity;
import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;

abstract public class BaseRouterImpl<A extends BaseActivity> {
	private A activity;
	private FragmentManager fragmentManager;

	public BaseRouterImpl(A activity) {
		this.activity = activity;
		this.fragmentManager = activity.getFragmentManager();
	}

	/**
	 * Adds a {@link Fragment} to this activity's layout.
	 *
	 * @param containerViewId The container view to where add the fragment.
	 * @param fragment        The fragment to be added.
	 */
	protected void addFragment(int containerViewId, Fragment fragment) {
		fragmentManager.beginTransaction()
						.add(containerViewId, fragment)
						.commit();
	}

	protected void addFragment(int containerViewId, BaseFragment fragment) {
		fragmentManager.beginTransaction()
						.add(containerViewId, fragment)
						.commit();
	}

//	protected void addFragment(int containerViewId, BaseFragment fragment, FragmentNavigation.Presenter presenter) {
//		try {
//			//ataching to fragment the navigation presenter
//			fragment.atachPresenter(presenter);
//			//showing the presenter on screen
//			fragmentManager.beginTransaction()
//							.add(containerViewId, fragment)
//							.commit();
//		} catch (NullPointerException e) {
//			Log.e(Consts.TAG, "BaseRouterImpl.addFragment\n" + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	protected void replaceFragment(int containerViewId, Fragment fragment) {
		fragmentManager.beginTransaction()
						.replace(containerViewId, fragment)
						//.addToBackStack(null) //возврат на предыдующий фрагмент
						.commit();
	}
//	private void replaceFragment(Fragment fragment, boolean addBackStack) {
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		transaction.replace(R.id.container, fragment, TAG);
//		if (addBackStack) transaction.addToBackStack(null);
//		transaction.commit();
//	}

	protected void replaceFragment(int containerViewId, BaseFragment fragment) {
		fragmentManager.beginTransaction()
						.replace(containerViewId, fragment)
						.commit();
	}

//	protected void replaceFragment(int containerViewId, BaseFragment fragment, FragmentNavigation.Presenter presenter) {
//		try {
//			//ataching to fragment the navigation presenter
//			fragment.atachPresenter(presenter);
//			//showing the presenter on screen
//			fragmentManager.beginTransaction()
//							.replace(containerViewId, fragment)
//							.commit();
//		} catch (NullPointerException e) {
//			Log.e(Consts.TAG, "BaseRouterImpl.addFragment\n" + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	public void startActivity(Class activityClass) {
		try {
			Intent intent = new Intent(activity, activityClass);
			activity.startActivity(intent);
		} catch (NullPointerException e) {
			Log.e(Consts.TAG, "BaseRouterImpl.startActivity\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void startActivity(Intent intentToLaunch) {
		try {
			activity.startActivity(intentToLaunch);
		} catch (NullPointerException e) {
			Log.e(Consts.TAG, "BaseRouterImpl.startActivity\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void startActivityForResult(Class activityClass, int requestCode) {
		try {
			Intent intent = new Intent(activity, activityClass);
			activity.startActivityForResult(intent, requestCode);
		} catch (NullPointerException e) {
			Log.e(Consts.TAG, "BaseRouterImpl.startActivityForResult\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}
