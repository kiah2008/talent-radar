package com.menatwork.test.service;

import android.test.AndroidTestCase;

import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;

public class ShareLocationAndGetUsersTest extends AndroidTestCase {

	public void testGettingResponse() throws Exception {
		// TODO - should do some kind of assert... at least acts as a smoke test
		// - miguel - 03/08/2012
		final ShareLocationAndGetUsersResponse response = ShareLocationAndGetUsers
				.newInstance(getContext(), "1", 0, 0, 30).execute();

		System.out.println(response);
	}

}
