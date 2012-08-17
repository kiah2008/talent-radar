package com.menatwork.service.response;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;
import com.menatwork.service.ResponseException;

public class ShareLocationAndGetUsersResponse extends BaseResponse {

	public ShareLocationAndGetUsersResponse(final JSONObject response) {
		super(response);
	}

	public List<? extends User> parseSurroundingUsers() {
		Log.i("users obtained from sharing location", getResponse().toString());
		try {
			final LinkedList<User> surroundingUsers = new LinkedList<User>();

			final JSONArray usersArray = getResponse().getJSONObject("result")
					.getJSONArray("users");

			final int usersArrayLength = usersArray.length();
			JSONObject userDuple;
			for (int i = 0; i < usersArrayLength; i++) {
				userDuple = usersArray.getJSONObject(i);

				final User user = parseUserDuple(userDuple);
				surroundingUsers.add(user);
			}

			return surroundingUsers;
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

	private User parseUserDuple(final JSONObject userDuple)
			throws JSONException {
		parseOnlineStatus(userDuple.getJSONObject("UsersOnline"));
		final User user = parseUser(userDuple.getJSONObject("User"));

		return user;
	}

	private User parseUser(final JSONObject userJson) throws JSONException {
		final UserBuilder userBuilder = UserBuilder.newInstance();

		userBuilder.setId(userJson.getString("id"));
		// FIXME - Woooow, wait a minute here... User name ain't username! -
		// miguel - 03/08/2012
			// XXX - username is nickname (not yet implemented)
			// alme - 07/06/2012
		// userBuilder.setUsername(userJson.getString("username"));
		userBuilder.setUserName(userJson.getString("name"));
		userBuilder.setUserSurname(userJson.getString("surname"));
		userBuilder.setEmail(userJson.getString("email"));
		userBuilder.setHeadline(userJson.getString("headline"));
		// userBuilder.setExtract(userJsonObject.getString("extract"));

		return userBuilder.build();
	}

	/**
	 * "id":"5", <br />
	 * "user_id":"1", <br />
	 * "duration":"30", <br />
	 * "latitude":"1", <br />
	 * "longitude":"1", <br />
	 * "created":"2012-07-05 09:37:56", <br />
	 * "modified":"2012-07-27 16:16:47"
	 *
	 * @param onlineJson
	 *
	 * @throws JSONException
	 */
	private void parseOnlineStatus(final JSONObject onlineJson)
			throws JSONException {
		// TODO - and someday we may know if this will be helpful or not -
		// miguel - 03/08/2012
		onlineJson.getString("id");
		onlineJson.getString("user_id");
		onlineJson.getString("duration");
		onlineJson.getString("latitude");
		onlineJson.getString("longitude");
		onlineJson.getString("created");
		onlineJson.getString("modified");
	}
}
