package com.menatwork.test.service;

import java.util.List;

import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.menatwork.model.User;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;

public class ShareLocationAndGetUsersResponseTest extends AndroidTestCase {

	public void testNoUsersAround() throws Exception {
		final JSONObject noUsersJson = new JSONObject(
				"{\"result\":{\"status\":\"ok\",\"users\":[]},\"status\":\"ok\"}");

		final ShareLocationAndGetUsersResponse response = new ShareLocationAndGetUsersResponse(
				noUsersJson);

		assertTrue("response succesful", response.isSuccessful());
		assertEquals("no surrounding users", //
				0, //
				response.parseSurroundingUsers().size());
	}

	public void testAMonsterApproaching() throws Exception {
		final JSONObject aMonsterAroundJson = new JSONObject(
				"{"
						+ "	\"status\":\"ok\","
						+ "	\"result\":{"
						+ "		\"status\":\"ok\","
						+ "		\"users\":[{"
						+ "			\"UsersOnline\":{"
						+ "				\"id\":\"5\","
						+ "				\"user_id\":\"1\","
						+ "				\"duration\":\"30\","
						+ "				\"latitude\":\"1\","
						+ "				\"longitude\":\"1\","
						+ "				\"created\":\"2012-07-05 09:37:56\","
						+ "				\"modified\":\"2012-07-27 16:16:47\""
						+ "			},"
						+ "			\"User\":{"
						+ "				\"id\":\"1\","
						+ "				\"auth_token\":null,"
						+ "				\"email\":\"mikewazowski@monster.inc\","
						+ "				\"password\":\"855bbd002e00b557b2c1885535c9980111e9949f\","
						+ "				\"username\":\"monstrous-mike!\","
						+ "				\"name\":\"Mike\","
						+ "				\"surname\":\"Wazowski\","
						+ "				\"birthday\":null,"
						+ "				\"linkedin_key\":null,"
						+ "				\"linkedin_secret\":null,"
						+ "				\"headline\":\"Monster!\"," //
						+ "				\"extract\":null,"
						+ "				\"created\":\"2012-06-21 00:00:46\","
						+ "				\"modified\":\"2012-06-21 00:00:46\"" //
						+ "			}" //
						+ "		}]" //
						+ "	}" //
						+ "}");

		final ShareLocationAndGetUsersResponse response = new ShareLocationAndGetUsersResponse(
				aMonsterAroundJson);

		assertTrue("response succesful", response.isSuccessful());

		final List<? extends User> surroundingUsers = response
				.parseSurroundingUsers();

		assertNotNull("surrounding users not null", surroundingUsers);
		assertEquals("surrounding users size is 1", //
				1, //
				surroundingUsers.size());

		final User user = surroundingUsers.iterator().next();

		assertEquals("surrounding user id", //
				"1", //
				user.getId());
		assertEquals("surrounding user full name", //
				"Mike Wazowski", //
				user.getDisplayableLongName());
		// FIXME - see FIXME in UserBuilder class - miguel - 03/08/2012
		// assertEquals("surrounding user username", //
		// "monstrous-mike!", //
		// user.getUsername());
		assertEquals("surrounding user headline", //
				"Monster!", //
				user.getHeadline());
		assertEquals("surrounding user email", //
				"mikewazowski@monster.inc", //
				user.getEmail());
	}
}
