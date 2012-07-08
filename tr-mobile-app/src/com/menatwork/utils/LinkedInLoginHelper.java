package com.menatwork.utils;

import android.content.Intent;
import android.net.Uri;

import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;

public class LinkedInLoginHelper {

	private static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	private static final String OAUTH_CALLBACK_HOST = "callback";
	private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://" + OAUTH_CALLBACK_HOST;

	private final String apiKey;
	private final String apiSecret;
	private LinkedInOAuthService oAuthService;
	private LinkedInRequestToken liToken;

	public LinkedInLoginHelper(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public Intent getLoginIntent() {
		oAuthService = LinkedInOAuthServiceFactory.getInstance()
				.createLinkedInOAuthService(apiKey, apiSecret);
		liToken = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
		Intent loginIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(liToken
				.getAuthorizationUrl()));
		return loginIntent;
	}

	public LinkedInAccessToken getAccessToken(Intent intent) {
		String verifier = intent.getData().getQueryParameter("oauth_verifier");
		LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(
				liToken, verifier);
		return accessToken;
	}

}