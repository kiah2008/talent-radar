package com.menatwork.service.response;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.menatwork.model.User;
import com.menatwork.service.ResponseException;

public class ShareLocationAndGetUsersResponse extends BaseResponse {

	public ShareLocationAndGetUsersResponse(final JSONObject response) {
		super(response);
	}

	public List<? extends User> parseSurroundingUsers() {
		Log.i("users obtained from sharing location", getResponse().toString());
		try {
			final JSONObject usersObject = getResponse().getJSONObject("result")
					.getJSONObject("users");

			final LinkedList<User> surroundingUsers = new LinkedList<User>();
			final int usersArrayLength = usersObject.length();

			JSONObject userDuple;
			for (int i = 0; i < usersArrayLength; i++) {
				final String userIndex = String.valueOf(i);

				userDuple = usersObject.getJSONObject(userIndex);
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
		return new JsonUserParser(userJson).parse().build();
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
