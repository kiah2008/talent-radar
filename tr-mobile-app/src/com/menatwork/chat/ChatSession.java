package com.menatwork.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.menatwork.model.ChatMessage;

public class ChatSession {
	public static ChatSession newInstance(String fromId, String toId) {
		return new ChatSession(fromId, toId);
	}

	final private String fromId, toId;
	private List<ChatMessage> messages;
	private List<SessionListener> listeners;

	private ChatSession(String fromId, String toId) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.messages = new LinkedList<ChatMessage>();
		this.listeners = new ArrayList<ChatSession.SessionListener>();
	}

	public String getFromId() {
		return fromId;
	}

	public String getToId() {
		return toId;
	}

	public void addMessage(ChatMessage message) {
		this.messages.add(message);
		this.notifyMessageListeners(message);
	}

	private void notifyMessageListeners(ChatMessage message) {
		for (SessionListener listener : listeners) {
			listener.onNewMessage(this, message);
		}
	}

	public List<ChatMessage> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public interface SessionListener {

		void onNewMessage(ChatSession chatSession, ChatMessage message);

	}

	public void addSessionListener(SessionListener listener) {
		this.listeners.add(listener);
	}

	public void removeSessionListener(SessionListener listener) {
		this.listeners.remove(listener);
	}
}