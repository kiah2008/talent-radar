package com.menatwork.service;

import com.menatwork.R;
import com.menatwork.service.response.SaveDeviceIdResponse;

import android.content.Context;

public class SaveDeviceId extends StandardServiceCall<SaveDeviceIdResponse> {

	private SaveDeviceId(Context context,
			Class<SaveDeviceIdResponse> responseClass, String userId,
			String deviceId) {
		super(context, responseClass);
		this.setParameter(//
				getString(R.string.post_key_save_device_id_user_id), //
				userId);
		this.setParameter(
				getString(R.string.post_key_save_device_id_device_id), //
				deviceId);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_save_device_id);
	}

}
