package com.menatwork.chat;

import java.util.HashMap;
import java.util.Map;

import com.menatwork.TalentRadarApplication;

public class ChatSessionManager {

	private Map<Integer, ChatSession> chatSessionsByIdOfInterlocutor;
	private TalentRadarApplication application;

	public static ChatSessionManager newInstance(
			TalentRadarApplication application) {
		return new ChatSessionManager(application);
	}

	private ChatSessionManager(TalentRadarApplication application) {
		this.application = application;
		chatSessionsByIdOfInterlocutor = new HashMap<Integer, ChatSession>();
	}

	public ChatSession getChatSessionByUserId(String userId) {
		int id = parseUserId(userId);
		// lazy "instantiation" of chat sessions
		if (!chatSessionsByIdOfInterlocutor.containsKey(id))
			initializeChatSession(id);
		return chatSessionsByIdOfInterlocutor.get(id);
	}

	private void initializeChatSession(int id) {
		String localUserId = application.getLocalUserId();
		chatSessionsByIdOfInterlocutor.put(id,
				ChatSession.newInstance(localUserId, String.valueOf(id)));
	}

	private int parseUserId(String userId) {
		try {
			return Integer.valueOf(userId);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Not a valid user id", e);
		}
	}

}
