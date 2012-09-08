package com.menatwork.gcm;

import android.content.Intent;

import com.menatwork.GCMIntentService;

public interface GcmMessageHandler {

	public void handle(GCMIntentService context, Intent extras);

}
