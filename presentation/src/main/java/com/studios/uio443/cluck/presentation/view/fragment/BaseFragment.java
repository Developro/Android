package com.studios.uio443.cluck.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import dagger.android.DaggerFragment;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */

/**
 * the base fragment implements the navigation view
 * to set the default methods implementation
 */
public abstract class BaseFragment extends DaggerFragment implements FragmentNavigation.View {

	// the root view
	protected View rootView;
	/**
	 * navigation presenter instance
	 * declared in base for easier access
	 */
	//protected FragmentNavigation.Presenter navigationPresenter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
					savedInstanceState) {
		rootView = inflater.inflate(getLayout(), container, false);
		return rootView;
	}

	protected abstract int getLayout();

//	@Override
//	public void atachPresenter(FragmentNavigation.Presenter presenter) {
//		navigationPresenter = presenter;
//	}

	/**
	 * Shows a {@link android.widget.Toast} message.
	 *
	 * @param message An string representing a message to be shown.
	 */
	protected void showToastMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

//    /**
//     * Adds a {@link Fragment} to this activity's layout.
//     *
//     * @param containerViewId The container view to where add the fragment.
//     * @param fragment        The fragment to be added.
//     */
//    protected void addFragment(int containerViewId, Fragment fragment) {
//        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
//        fragmentTransaction.add(containerViewId, fragment);
//        fragmentTransaction.commit();
//    }
}
