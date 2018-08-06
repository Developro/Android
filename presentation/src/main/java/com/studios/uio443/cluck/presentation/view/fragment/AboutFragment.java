package com.studios.uio443.cluck.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.util.Consts;

/**
 * Created by zundarik
 */

public class AboutFragment extends BaseFragment {

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		Log.d(Consts.TAG, "AboutFragment.onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_about;
	}
}
