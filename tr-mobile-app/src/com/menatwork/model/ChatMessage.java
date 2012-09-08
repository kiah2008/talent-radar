package com.menatwork.model;

public class ChatMessage {

	private final String messageId;
	private final String fromId;
	private final String toId;
	private final String message;

	public static ChatMessage newInstance(String messageId, String fromId,
			String toId, String message) {
		return new ChatMessage(messageId, fromId, toId, message);
	}

	private ChatMessage(String messageId, String fromId, String toId,
			String message) {
		super();
		this.messageId = messageId;
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getFromId() {
		return fromId;
	}

	public String getToId() {
		return toId;
	}

	public String getMessage() {
		return message;
	}

}