package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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

public class PingAlertActivity extends TalentRadarActivity {

	private static final int DIALOG_IGNORE_OR_BAN = 0;
	private TextView username;
	private TextView message;
	private ImageView profilePicture;
	private Button acceptButton;
	private Button declineButton;
	private ProgressBar loadingProfilePic;

	private String pingId = "64";
	private String senderName = "Pepe Monje";

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
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_IGNORE_OR_BAN:
			builder.setMessage(String.format(
					getString(R.string.ping_alert_confirm_ban_message),
					senderName));
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
		new ReplyPingTask().execute(pingId, answer);
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
					getString(R.string.ping_alert_confirm_wait));
		}

		@Override
		protected Response doInBackground(Object... params) {
			try {
				answer = (Answer) params[1];
				ReplyPing replyPing = ReplyPing.newInstance(
						PingAlertActivity.this, (String) params[0], answer);
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
				;
			else
				finish();
		}

	}
}
