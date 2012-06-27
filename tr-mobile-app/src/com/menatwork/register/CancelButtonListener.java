package com.menatwork.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.menatwork.LoginActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class CancelButtonListener extends StartActivityOnClickListener {

	public CancelButtonListener(Activity source) {
		super(source, LoginActivity.class);
	}

	@Override
	public void onClick(final View v) {
		AlertDialog dialog = new AlertDialog.Builder(source).create();
		dialog.setMessage(source.getString(R.string.register_cancel_message));
		dialog.setButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CancelButtonListener.super.onClick(v);
			}
		});
		dialog.show();
	}
}
