package com.studios.uio443.cluck.presentation.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.util.Consts;

public class FeedbackFragment extends BaseFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(Consts.TAG, "FeedbackFragment.onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Log.d(Consts.TAG, "Send!");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, ((EditText) view1.findViewById(R.id.feedback_text)).getText().toString());
            sendIntent.putExtra(Intent.EXTRA_EMAIL, ((EditText) view1.findViewById(R.id.feedback_email)).getText().toString());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "feedback from " + ((EditText) view1.findViewById(R.id.feedback_name)).getText().toString());
            sendIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, 123456789);
            //sendIntent.setType("text/plain");
            sendIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(sendIntent, "Send us message by email"));

            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_feedback;
    }
}
