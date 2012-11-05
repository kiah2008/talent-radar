package com.menatwork.chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.menatwork.model.ChatMessage;

public class ChatSession {
	public static ChatSession newInstance(final String fromId, final String toId) {
		return new ChatSession(fromId, toId);
	}

	final private String fromId, toId;
	private final List<ChatMessage> messages;
	private final Set<SessionListener> listeners;

	private ChatSession(final String fromId, final String toId) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.messages = new LinkedList<ChatMessage>();
		this.listeners = new HashSet<SessionListener>();
	}

	public String getFromId() {
		return fromId;
	}

	public String getToId() {
		return toId;
	}

	public void addMessage(final ChatMessage message) {
		this.messages.add(message);
		this.notifyMessageListeners(message);
	}

	private void notifyMessageListeners(final ChatMessage message) {
		for (final SessionListener listener : listeners)
			listener.onNewMessage(this, message);
	}

	public List<ChatMessage> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public interface SessionListener {

		void onNewMessage(ChatSession chatSession, ChatMessage message);

	}

	public void addSessionListener(final SessionListener listener) {
		this.listeners.add(listener);
	}

	public void removeSessionListener(final SessionListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * This method won't notify any listeners since it is triggered from the UI,
	 * therefore, the one calling it should know what to do (besides, it would
	 * mess up any logic depending on the listeners, since these messages are
	 * old ones)
	 * 
	 * @param oldMessages
	 */
	public void addOldMessages(final List<ChatMessage> oldMessages) {
		this.messages.addAll(0, oldMessages);
	}
}