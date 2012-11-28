package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.menatwork.service.ReplyPing;
import com.menatwork.service.ReplyPing.Answer;
import com.menatwork.service.response.ErroneousResponse;
import com.menatwork.service.response.Response;
import com.menatwork.view.LoadProfilePictureTask;

public class PingAlertActivity extends GuiTalentRadarActivity {

	private static final int DIALOG_IGNORE_OR_BAN = 0;
	public static final String EXTRA_USER_ID = "userid";
	public static final String EXTRA_MESSAGE = "message";
	public static final String EXTRA_USER_FULLNAME = "fullname";
	public static final String EXTRA_PING_ID = "pingid";
	public static final String EXTRAS_PROFILE_PIC_URL = "profilePicUrl";

	private TextView username;
	private TextView message;
	private ImageView profilePicture;
	private Button acceptButton;
	private Button declineButton;
	private ProgressBar loadingProfilePic;

	private String pingId;
	private String dataUserName;
	private String dataMessage;
	private String userId;
	private String profilePicUrl;

	@Override
	protected void onResume() {
		this.loadDataFromExtras();
		super.onResume();
	}

	private void loadProfilePic(final String profilePicUrl) {
		new LoadProfilePictureTask(this, profilePicture, loadingProfilePic, profilePicUrl).execute();
	}

	private void loadDataFromExtras() {
		final Bundle extras = getIntent().getExtras();
		pingId = extras.getString(EXTRA_PING_ID);
		dataUserName = extras.getString(EXTRA_USER_FULLNAME);
		dataMessage = extras.getString(EXTRA_MESSAGE);
		userId = extras.getString(EXTRA_USER_ID);
		profilePicUrl = extras.getString(EXTRAS_PROFILE_PIC_URL);
		
		if (pingId == null || //
				dataUserName == null || //
				dataMessage == null || //
				userId == null || //
				profilePicUrl == null)
			throw new RuntimeException("This activity must be called with extras");
		
		username.setText(dataUserName);
		message.setText(dataMessage);
		this.loadProfilePic(profilePicUrl);

	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.ping_alert;
	}

	@Override
	protected void findViewElements() {
		username = findTextViewById(R.id.ping_alert_username);
		message = findTextViewById(R.id.ping_alert_message);
		profilePicture = findImageViewById(R.id.ping_alert_profile_pic);
		acceptButton = findButtonById(R.id.ping_alert_button_accept);
		declineButton = findButtonById(R.id.ping_alert_button_decline);
		loadingProfilePic = (ProgressBar) findViewById(R.id.ping_alert_loading_profile_pic);
	}

	@Override
	protected void setupButtons() {
		declineButton.setOnClickListener(new DeclineButtonListener());
		acceptButton.setOnClickListener(new AcceptButtonListener());
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_IGNORE_OR_BAN:
			builder.setMessage(String
					.format(getString(R.string.ping_alert_confirm_ban_message), dataUserName));
			builder.setNegativeButton(R.string.ping_alert_confirm_ban_no, new BanDialogListener());
			builder.setPositiveButton(R.string.ping_alert_confirm_ban_ok, new BanDialogListener());
			builder.setNeutralButton(R.string.ping_alert_confirm_ban_cancel, new BanDialogListener());
		}
		return builder.create();
	}

	void replyPing(final Answer answer) {
		// FIXME - local user is not loaded when this activity raises from
		// nowhere (from an incoming ping) and getId() crashes - boris -
		// 28/11/2012
		new ReplyPingTask().execute(getTalentRadarApplication().getLocalUser().getId(), pingId, answer);
	}

	private void proceedToChatInterface() {
		final Intent intent = new Intent(PingAlertActivity.this, ChatActivity.class);
		// TODO - get the real headline (along with the profile pic)
		intent.putExtra(ChatActivity.EXTRAS_HEADLINE, "");
		intent.putExtra(ChatActivity.EXTRAS_USER_ID, userId);
		intent.putExtra(ChatActivity.EXTRAS_USERNAME, dataUserName);
		intent.putExtra(ChatActivity.EXTRAS_PROFILE_PIC_URL, profilePicUrl);
		startActivity(intent);
	}

	private class AcceptButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			replyPing(Answer.ACCEPT);
			proceedToChatInterface();
		}

	}

	private class DeclineButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			showDialog(DIALOG_IGNORE_OR_BAN);
		}

	}

	private class BanDialogListener implements android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(final DialogInterface dialog, final int which) {
			switch (which) {
			case DialogInterface.BUTTON_NEUTRAL:
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				replyPing(ReplyPing.Answer.IGNORE);
				finish();
				break;
			case DialogInterface.BUTTON_POSITIVE:
				replyPing(ReplyPing.Answer.BAN);
				finish();
				break;
			}
		}
	}

	private class ReplyPingTask extends AsyncTask<Object, Void, Response> {

		// private ProgressDialog progressDialog;
		private Answer answer;

		@Override
		protected void onPreExecute() {
			// progressDialog = ProgressDialog.show(PingAlertActivity.this, "",
			// getString(R.string.generic_wait));
		}

		@Override
		protected Response doInBackground(final Object... params) {
			try {
				answer = (Answer) params[2];
				final String localUserId = (String) params[0];
				final String pingId = (String) params[1];
				final ReplyPing replyPing = ReplyPing.newInstance(PingAlertActivity.this, localUserId,
						pingId, answer);
				return replyPing.execute();
			} catch (final JSONException e) {
				Log.e("ReplyPingTask", "Error receiving response");
				e.printStackTrace();
			} catch (final IOException e) {
				Log.e("ReplyPingTask", "Error receiving response");
				e.printStackTrace();
			}
			return ErroneousResponse.INSTANCE;
		}

		@Override
		protected void onPostExecute(final Response result) {
			// progressDialog.dismiss();
			if (!result.isSuccessful())
				Toast.makeText(PingAlertActivity.this, getString(R.string.generic_error), Toast.LENGTH_SHORT)
						.show();
			if (Answer.ACCEPT.equals(answer))
				// launch chat interface
				// proceedToChatInterface();
				;
			else
				// finish();
				;
		}
	}
}
