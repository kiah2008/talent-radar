package com.menatwork.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.menatwork.R;
import com.menatwork.hunts.DefaultHunt;
import com.menatwork.model.User;

public abstract class SaveContactBaseTask<T> extends AsyncTask<T, Void, Void> {

	protected Context context;
	protected Handler handler;

	public SaveContactBaseTask(final Context context) {
		this.context = context;
		initializeHandler();
	}

	private void initializeHandler() {
		handler = new Handler(Looper.getMainLooper());
	}

	protected void saveContact(final User user) {
		DefaultHunt.getInstance().addUser(user);

		final String saveContactSuccessString = String.format(
				context.getString(R.string.save_contact_success_format),
				user.getDisplayableShortName());

		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, saveContactSuccessString,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

}
