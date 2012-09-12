package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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

	@Override
	protected void onResume() {
		this.loadDataFromExtras();
		this.loadProfilePic();
		super.onResume();
	}

	private void loadProfilePic() {
		new LoadProfilePictureTask(profilePicture, loadingProfilePic,
				"http://www.krizna.net/wp-content/uploads/2012/03/yao-ming-meme_facebook_1.jpg")
				.execute();
	}

	private void loadDataFromExtras() {
		Bundle extras = getIntent().getExtras();
		pingId = extras.getString(EXTRA_PING_ID);
		dataUserName = extras.getString(EXTRA_USER_FULLNAME);
		dataMessage = extras.getString(EXTRA_MESSAGE);
		userId = extras.getString(EXTRA_USER_ID);
		username.setText(dataUserName);
		message.setText(dataMessage);
		// TODO - load profile pic
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
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_IGNORE_OR_BAN:
			builder.setMessage(String.format(
					getString(R.string.ping_alert_confirm_ban_message),
					dataUserName));
			builder.setNegativeButton(R.string.ping_alert_confirm_ban_no,
					new BanDialogListener());
			builder.setPositiveButton(R.string.ping_alert_confirm_ban_ok,
					new BanDialogListener());
			builder.setNeutralButton(R.string.ping_alert_confirm_ban_cancel,
					new BanDialogListener());
		}
		return builder.create();
	}

	void replyPing(Answer answer) {
		new ReplyPingTask().execute(getTalentRadarApplication().getLocalUser()
				.getId(), pingId, answer);
	}

	private void proceedToChatInterface() {
		Intent intent = new Intent(PingAlertActivity.this, ChatActivity.class);
		// TODO - get the real headline (along with the profile pic)
		intent.putExtra(ChatActivity.EXTRAS_HEADLINE,
				"this would be a headline (cuac)");
		intent.putExtra(ChatActivity.EXTRAS_USER_ID, userId);
		intent.putExtra(ChatActivity.EXTRAS_USERNAME, dataUserName);
		startActivity(intent);
	}

	private class AcceptButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			replyPing(Answer.ACCEPT);
		}

	}

	private class DeclineButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			showDialog(DIALOG_IGNORE_OR_BAN);
		}

	}

	private class BanDialogListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_NEUTRAL:
				dialog.dismiss();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				replyPing(ReplyPing.Answer.IGNORE);
				break;
			case DialogInterface.BUTTON_POSITIVE:
				replyPing(ReplyPing.Answer.BAN);
				break;
			}
		}
	}

	private class ReplyPingTask extends AsyncTask<Object, Void, Response> {

		private ProgressDialog progressDialog;
		private Answer answer;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(PingAlertActivity.this, "",
					getString(R.string.generic_wait));
		}

		@Override
		protected Response doInBackground(Object... params) {
			try {
				answer = (Answer) params[2];
				String localUserId = (String) params[0];
				String pingId = (String) params[1];
				ReplyPing replyPing = ReplyPing.newInstance(
						PingAlertActivity.this, localUserId, pingId, answer);
				return replyPing.execute();
			} catch (JSONException e) {
				Log.e("ReplyPingTask", "Error receiving response");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("ReplyPingTask", "Error receiving response");
				e.printStackTrace();
			}
			return ErroneousResponse.INSTANCE;
		}

		@Override
		protected void onPostExecute(Response result) {
			progressDialog.dismiss();
			if (!result.isSuccessful()) {
				Toast.makeText(PingAlertActivity.this,
						getString(R.string.generic_error), Toast.LENGTH_SHORT)
						.show();
			}
			if (Answer.ACCEPT.equals(answer))
				// launch chat interface
				// TODO - prototype implementation - alme
				proceedToChatInterface();
			else
				finish();
		}

	}
}
