package com.menatwork.model;

public class ChatMessage {

	private final String messageId;
	private final String fromId;
	private final String toId;
	private final String message;

	public static ChatMessage newInstance(final String messageId, final String fromId,
			final String toId, final String message) {
		return new ChatMessage(messageId, fromId, toId, message);
	}

	private ChatMessage(final String messageId, final String fromId, final String toId,
			final String message) {
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

	@Override
	public String toString() {
		return "ChatMessage [messageId=" + messageId + ", fromId=" + fromId
				+ ", toId=" + toId + ", message=" + message + "]";
	}
	
	

}