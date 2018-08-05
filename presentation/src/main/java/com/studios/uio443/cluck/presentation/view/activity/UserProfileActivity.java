/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.studios.uio443.cluck.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;
import com.studios.uio443.cluck.presentation.view.fragment.UserProfileFragment;

/**
 * Activity that shows details of a certain user.
 */
public class UserProfileActivity extends BaseActivity {

  private static final String INTENT_EXTRA_PARAM_USER_ID = "org.android10.INTENT_PARAM_USER_ID";
  private static final String INSTANCE_STATE_PARAM_USER_ID = "org.android10.STATE_PARAM_USER_ID";

  public static Intent getCallingIntent(Context context, int userId) {
    Intent callingIntent = new Intent(context, UserProfileActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
    return callingIntent;
  }

  private int userId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    if (savedInstanceState == null) {
      this.userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
      addFragment(R.id.fragmentContainer, UserProfileFragment.forUser(userId));
    } else {
      this.userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
    }
  }

	protected void addFragment(int containerViewId, BaseFragment fragment) {
		this.getFragmentManager().beginTransaction()
						.add(containerViewId, fragment)
						.commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putInt(INSTANCE_STATE_PARAM_USER_ID, this.userId);
		}
		super.onSaveInstanceState(outState);
	}
}
