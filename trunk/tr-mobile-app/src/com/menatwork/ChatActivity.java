package com.menatwork;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.menatwork.chat.ChatSession;
import com.menatwork.chat.ChatSession.SessionListener;
import com.menatwork.model.ChatMessage;
import com.menatwork.service.AddChatMessage;
import com.menatwork.service.GetMessages;
import com.menatwork.service.response.ErroneousResponse;
import com.menatwork.service.response.GetMessagesResponse;
import com.menatwork.service.response.Response;
import com.menatwork.view.LoadProfilePictureTask;

public class ChatActivity extends GuiTalentRadarActivity implements
		SessionListener {

	public static final String EXTRAS_USER_ID = "userid";
	public static final String EXTRAS_HEADLINE = "headline";
	public static final String EXTRAS_USERNAME = "username";
	public static final String EXTRAS_PROFILE_PIC_URL = "profilePicUrl";
	public static final String OLD_MESSAGES_AMOUNT_TO_REFRESH = "10";

	private ViewGroup chatLayout;
	private TextView username;
	private TextView headline;
	private TextView input;
	private Button sendButton;
	private ProgressBar loadingProfilePic;
	private ImageView profilePicture;

	private ChatSession chatSession;
	private Handler handler;
	private ScrollView scrollView;
	private Button olderMessagesButton;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		initializeActivity();
	}

	private void initializeActivity() {
		this.initializeHandler();
		final String userid = this.loadDataFromExtras();
		this.ensureConsistencyOfChatSession(userid);
		this.loadExistingMessagesIntoView();
	}

	@Override
	protected void onNewIntent(final Intent intent) {
		this.setIntent(intent);
		// call postCreate so as to have the same behavior with new/reused
		// instances of this activity
		this.initializeActivity();
	}

	private void initializeHandler() {
		handler = new Handler(Looper.getMainLooper());
	}

	private void loadExistingMessagesIntoView() {
		this.chatLayout.removeAllViews();
		final List<ChatMessage> messages = this.chatSession.getMessages();
		for (final ChatMessage chatMessage : messages)
			this.appendMessageToView(chatMessage);
		// TODO - when this is inside a scroll view, do a fullScroll down
	}

	private void ensureConsistencyOfChatSession(final String userid) {
		if (chatSession == null)
			loadChatSessionForUserId(userid);
		// or...
		else if (!chatSession.getToId().equals(userid)) {
			chatSession.removeSessionListener(this);
			loadChatSessionForUserId(userid);
		}
		this.chatSession.addSessionListener(this);
	}

	private void loadChatSessionForUserId(final String userid) {
		chatSession = getTalentRadarApplication().getChatSessionManager()
				.getChatSessionByUserId(userid);
	}

	private String loadDataFromExtras() {
		final Bundle extras = getIntent().getExtras();
		final String dataUsername = extras.getString(EXTRAS_USERNAME);
		final String dataHeadline = extras.getString(EXTRAS_HEADLINE);
		final String userid = extras.getString(EXTRAS_USER_ID);
		final String profilePicUrl = extras.getString(EXTRAS_PROFILE_PIC_URL);
		if (dataUsername == null || //
				dataHeadline == null || //
				userid == null || //
				profilePicUrl == null)
			throw new RuntimeException(
					"This activity must be called with extras");
		username.setText(dataUsername);
		headline.setText(dataHeadline);
		this.loadProfilePic(profilePicUrl);
		return userid;
	}

	@Override
	protected void setupButtons() {
		sendButton.setOnClickListener(new SendButtonListener());
		olderMessagesButton.setOnClickListener(new OlderMessagesListener());
	}

	@Override
	protected void findViewElements() {
		chatLayout = findViewGroupById(R.id.chat_layout_chat);
		username = findTextViewById(R.id.chat_username);
		headline = findTextViewById(R.id.chat_headline);
		input = findTextViewById(R.id.chat_input);
		sendButton = findButtonById(R.id.chat_button_send);
		profilePicture = findImageViewById(R.id.chat_profile_pic);
		loadingProfilePic = (ProgressBar) findViewById(R.id.chat_loading_profile_pic);
		scrollView = (ScrollView) findViewById(R.id.chat_scroll_view);
		olderMessagesButton = findButtonById(R.id.chat_older_messages);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.chat;
	}

	/**
	 * Append as last message.
	 * 
	 * @param message
	 */
	private void appendMessageToView(final ChatMessage message) {
		final int index = chatLayout.getChildCount();
		this.appendMessageToView(message, index);
	}

	/**
	 * Append message to the specified position on the chat
	 * 
	 * @param message
	 * @param index
	 */
	private void appendMessageToView(final ChatMessage message, final int index) {
		final TextView newMessageView = new TextView(this);
		final String displayableMessage = getDisplayableForm(message);
		newMessageView.setText(displayableMessage);
		chatLayout.addView(newMessageView, index);
	}

	private String getDisplayableForm(final ChatMessage message) {
		String from;
		if (getTalentRadarApplication().getLocalUserId().equals(
				message.getFromId()))
			from = getString(R.string.chat_message_label_self);
		else
			from = username.getText().toString().split(" ")[0];
		final StringBuilder stringBuilder = new StringBuilder(from);
		stringBuilder.append('\n');
		stringBuilder.append(message.getMessage());
		final String displayableMessage = stringBuilder.toString();
		return displayableMessage;
	}

	private void afterButtonSendPressed() {
		input.setText("");
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
	}

	private class SendButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final String message = input.getText().toString();
			// final String messageAsciiStandard = message.replaceAll(
			// "[^\\x00-\\x7F]", "");
			final String messageAsciiStandard = message;

			final String fromId = getTalentRadarApplication().getLocalUser()
					.getId();
			// TODO - pass the message to the task and, when the response comes,
			// stamp the message id into the chatMessage
			final String toId = chatSession.getToId();
			final ChatMessage chatMessage = ChatMessage.newInstance("dunno!",
					fromId, toId, message);
			chatSession.addMessage(chatMessage);
			new SendMessageTask().execute(fromId, toId, messageAsciiStandard);
			afterButtonSendPressed();
		}

	}

	private class SendMessageTask extends AsyncTask<String, Void, Response> {

		@Override
		protected Response doInBackground(final String... args) {
			try {
				final String fromId = args[0];
				final String toId = args[1];
				final String content = args[2];

				final AddChatMessage addChat = AddChatMessage.newInstance(
						ChatActivity.this, fromId, toId, content);
				return addChat.execute();
			} catch (final JSONException e) {
				Log.e("SendMessageTask", "Error receiving response");
				e.printStackTrace();
			} catch (final IOException e) {
				Log.e("SendMessageTask", "Error receiving response");
				e.printStackTrace();
			}
			return ErroneousResponse.INSTANCE;
		}

	}

	@Override
	public void onNewMessage(final ChatSession chatSession,
			final ChatMessage message) {
		if (chatSession.equals(this.chatSession)) {
			final Runnable updateViewWithMessageRunnable = new UpdateViewWithMessageRunnable(
					message);
			handler.post(updateViewWithMessageRunnable);
		} else
			throw new RuntimeException(
					"ChatActivity is listening to messages of unrelated chatSession");
	}

	private void loadProfilePic(final String profilePicUrl) {
		new LoadProfilePictureTask(this, profilePicture, loadingProfilePic,
				profilePicUrl).execute();
	}

	private final class UpdateViewWithMessageRunnable implements Runnable {
		private final ChatMessage message;

		private UpdateViewWithMessageRunnable(final ChatMessage message) {
			this.message = message;
		}

		@Override
		public void run() {
			appendMessageToView(message);
		}
	}

	private class OlderMessagesListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			new GetMessagesTask() {

				@Override
				protected void onPostExecute(final GetMessagesResponse result) {
					if (result != null && result.isSuccessful()) {
						final List<ChatMessage> oldMessages = result
								.getMessages();
						chatSession.addOldMessages(oldMessages);
						addOldMessagesToView(oldMessages);
					}
				}

			}.execute();
		}

	}

	public void addOldMessagesToView(final List<ChatMessage> oldMessages) {
		for (final ChatMessage oldMessage : oldMessages)
			appendMessageToView(oldMessage, oldMessages.indexOf(oldMessage));
	}

	private class GetMessagesTask extends
			AsyncTask<Void, Void, GetMessagesResponse> {

		@Override
		protected GetMessagesResponse doInBackground(final Void... arg0) {
			try {
				final GetMessages getMessages = GetMessages
						.newInstance(ChatActivity.this, chatSession.getToId(),
								chatSession.getFromId(),
								OLD_MESSAGES_AMOUNT_TO_REFRESH);

				return getMessages.execute();
			} catch (final JSONException e) {
				Log.e("GetMessagesTask", "Error receiving response");
				e.printStackTrace();
			} catch (final IOException e) {
				Log.e("GetMessagesTask", "Error receiving response");
				e.printStackTrace();
			}
			return null;
		}

	}
}
