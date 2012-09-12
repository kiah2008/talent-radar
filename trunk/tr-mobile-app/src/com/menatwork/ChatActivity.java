package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.menatwork.model.ChatMessage;
import com.menatwork.service.AddChatMessage;
import com.menatwork.service.response.ErroneousResponse;
import com.menatwork.service.response.Response;

public class ChatActivity extends GuiTalentRadarActivity {

	public static final String EXTRAS_USER_ID = "userid";
	public static final String EXTRAS_HEADLINE = "headline";
	public static final String EXTRAS_USERNAME = "username";
	private ViewGroup chatLayout;
	private TextView username;
	private TextView headline;
	private TextView input;
	private Button sendButton;
	private String userid;

	@Override
	protected void postCreate(Bundle savedInstanceState) {
		this.loadDataFromExtras();
	}

	private void loadDataFromExtras() {
		Bundle extras = getIntent().getExtras();
		String dataUsername = extras.getString(EXTRAS_USERNAME);
		String dataHeadline = extras.getString(EXTRAS_HEADLINE);
		userid = extras.getString(EXTRAS_USER_ID);
		if (dataUsername == null || //
				dataHeadline == null || //
				userid == null)
			throw new RuntimeException(
					"This activity must be called with extras");
		username.setText(dataUsername);
		headline.setText(dataHeadline);
	}

	@Override
	protected void setupButtons() {
		sendButton.setOnClickListener(new SendButtonListener());
	}

	@Override
	protected void findViewElements() {
		chatLayout = findViewGroupById(R.id.chat_layout_chat);
		username = findTextViewById(R.id.chat_username);
		headline = findTextViewById(R.id.chat_headline);
		input = findTextViewById(R.id.chat_input);
		sendButton = findButtonById(R.id.chat_button_send);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.chat;
	}

	public void appendMessage(ChatMessage message) {
		TextView newMessage = new TextView(this);
		// getTalentRadarApplication().getChatPrinter().print(newInstance);
		String from;
		if (getTalentRadarApplication().getLocalUserId().equals(
				message.getFromId()))
			from = getString(R.string.chat_message_label_self);
		else
			from = username.getText().toString().split(" ")[0];
		StringBuilder stringBuilder = new StringBuilder(from);
		stringBuilder.append('\n');
		stringBuilder.append(message.getMessage());
		newMessage.setText(stringBuilder.toString());
		chatLayout.addView(newMessage);
		input.setText("");
	}

	private class SendButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String message = input.getText().toString();
			String fromId = getTalentRadarApplication().getLocalUser().getId();
			appendMessage(ChatMessage.newInstance("dunno!", fromId, userid,
					message));
			new SendMessageTask().execute(fromId, userid, message);
		}

	}

	private class SendMessageTask extends AsyncTask<String, Void, Response> {

		@Override
		protected Response doInBackground(String... args) {
			try {
				String fromId = args[0];
				String toId = args[1];
				String content = args[2];
				AddChatMessage addChat = AddChatMessage.newInstance(
						ChatActivity.this, fromId, toId, content);
				return addChat.execute();
			} catch (JSONException e) {
				Log.e("SendMessageTask", "Error receiving response");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("SendMessageTask", "Error receiving response");
				e.printStackTrace();
			}
			return ErroneousResponse.INSTANCE;
		}

	}

}
