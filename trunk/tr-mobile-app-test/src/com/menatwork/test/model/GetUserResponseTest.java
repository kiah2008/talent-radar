package com.menatwork.test.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.menatwork.model.JobPosition;
import com.menatwork.model.User;
import com.menatwork.service.response.GetUserResponse;

public class GetUserResponseTest extends AndroidTestCase {

	public void testParseUserJustWithMainData() throws Exception {
		final User parsedUser = userFromJsonString("{\"status\":\"ok\",\"result\":{\"status\":\"ok\",\"user\":{\"User\":{\"id\":\"1\",\"auth_token\":null,\"email\":null,\"username\":\"mostrobora\",\"name\":\"Mostro\",\"surname\":\"Bora\",\"headline\":\"Cookie monster at Sessame Street\",\"picture\":\"http://m3.licdn.com/mpr/mprx/0_oivHPfsr3BHERRh1ECncPuu23rOIUYr1eXTBPuIrOnoRkVFPQL5Xj2xOfjYq4MA0IhB9yIJuE9-D\",\"show_name\":true,\"show_headline\":true,\"show_picture\":false,\"show_skills\":true,\"show_jobs\":false,\"show_in_searches\":true},\"UsersSkill\":{\"0\":{\"id\":\"106288\",\"user_id\":\"1\",\"skill_id\":\"7\"},\"1\":{\"id\":\"106289\",\"user_id\":\"1\",\"skill_id\":\"15\"},\"2\":{\"id\":\"106290\",\"user_id\":\"1\",\"skill_id\":\"19\"},\"3\":{\"id\":\"106291\",\"user_id\":\"1\",\"skill_id\":\"21\"},\"4\":{\"id\":\"106292\",\"user_id\":\"1\",\"skill_id\":\"24\"},\"5\":{\"id\":\"106293\",\"user_id\":\"1\",\"skill_id\":\"31\"},\"6\":{\"id\":\"106294\",\"user_id\":\"1\",\"skill_id\":\"50\"},\"7\":{\"id\":\"106295\",\"user_id\":\"1\",\"skill_id\":\"72\"},\"8\":{\"id\":\"106296\",\"user_id\":\"1\",\"skill_id\":\"73\"},\"9\":{\"id\":\"106297\",\"user_id\":\"1\",\"skill_id\":\"74\"},\"10\":{\"id\":\"106298\",\"user_id\":\"1\",\"skill_id\":\"75\"}},\"UsersStudy\":{}}}}");

		assertEquals("id", "1", parsedUser.getId());
		assertEquals("nickname", "mostrobora", parsedUser.getNickname());
		assertEquals("name", "Mostro", parsedUser.getName());
		assertEquals("surname", "Bora", parsedUser.getSurname());
		assertEquals("headline", "Cookie monster at Sessame Street",
				parsedUser.getHeadline());
	}

	public void testParseUserWithoutJobPositions() throws Exception {
		final User parsedUser = userFromJsonString("{\"status\":\"ok\",\"result\":{\"status\":\"ok\",\"user\":{\"User\":{\"id\":\"1\",\"auth_token\":null,\"email\":null,\"username\":\"mostrobora\",\"name\":\"Mostro\",\"surname\":\"Bora\",\"headline\":\"Cookie monster at Sessame Street\",\"picture\":\"http://m3.licdn.com/mpr/mprx/0_oivHPfsr3BHERRh1ECncPuu23rOIUYr1eXTBPuIrOnoRkVFPQL5Xj2xOfjYq4MA0IhB9yIJuE9-D\",\"show_name\":true,\"show_headline\":true,\"show_picture\":false,\"show_skills\":true,\"show_jobs\":false,\"show_in_searches\":true},\"UsersSkill\":{\"0\":{\"id\":\"106288\",\"user_id\":\"1\",\"skill_id\":\"7\"},\"1\":{\"id\":\"106289\",\"user_id\":\"1\",\"skill_id\":\"15\"},\"2\":{\"id\":\"106290\",\"user_id\":\"1\",\"skill_id\":\"19\"},\"3\":{\"id\":\"106291\",\"user_id\":\"1\",\"skill_id\":\"21\"},\"4\":{\"id\":\"106292\",\"user_id\":\"1\",\"skill_id\":\"24\"},\"5\":{\"id\":\"106293\",\"user_id\":\"1\",\"skill_id\":\"31\"},\"6\":{\"id\":\"106294\",\"user_id\":\"1\",\"skill_id\":\"50\"},\"7\":{\"id\":\"106295\",\"user_id\":\"1\",\"skill_id\":\"72\"},\"8\":{\"id\":\"106296\",\"user_id\":\"1\",\"skill_id\":\"73\"},\"9\":{\"id\":\"106297\",\"user_id\":\"1\",\"skill_id\":\"74\"},\"10\":{\"id\":\"106298\",\"user_id\":\"1\",\"skill_id\":\"75\"}},\"UsersStudy\":{}}}}");

		assertTrue("job positions should be empty", parsedUser
				.getJobPositions().isEmpty());
	}

	public void testParseUserWithJobPositionsEmpty() throws Exception {
		final User parsedUser = userFromJsonString("{\"status\":\"ok\",\"result\":{\"status\":\"ok\",\"user\":{\"User\":{\"id\":\"1\",\"auth_token\":null,\"email\":null,\"username\":\"mostrobora\",\"name\":\"Mostro\",\"surname\":\"Bora\",\"headline\":\"Cookie monster at Sessame Street\",\"picture\":\"http://m3.licdn.com/mpr/mprx/0_oivHPfsr3BHERRh1ECncPuu23rOIUYr1eXTBPuIrOnoRkVFPQL5Xj2xOfjYq4MA0IhB9yIJuE9-D\",\"show_name\":true,\"show_headline\":true,\"show_picture\":false,\"show_skills\":true,\"show_jobs\":false,\"show_in_searches\":true},\"UsersSkill\":{\"0\":{\"id\":\"106288\",\"user_id\":\"1\",\"skill_id\":\"7\"},\"1\":{\"id\":\"106289\",\"user_id\":\"1\",\"skill_id\":\"15\"},\"2\":{\"id\":\"106290\",\"user_id\":\"1\",\"skill_id\":\"19\"},\"3\":{\"id\":\"106291\",\"user_id\":\"1\",\"skill_id\":\"21\"},\"4\":{\"id\":\"106292\",\"user_id\":\"1\",\"skill_id\":\"24\"},\"5\":{\"id\":\"106293\",\"user_id\":\"1\",\"skill_id\":\"31\"},\"6\":{\"id\":\"106294\",\"user_id\":\"1\",\"skill_id\":\"50\"},\"7\":{\"id\":\"106295\",\"user_id\":\"1\",\"skill_id\":\"72\"},\"8\":{\"id\":\"106296\",\"user_id\":\"1\",\"skill_id\":\"73\"},\"9\":{\"id\":\"106297\",\"user_id\":\"1\",\"skill_id\":\"74\"},\"10\":{\"id\":\"106298\",\"user_id\":\"1\",\"skill_id\":\"75\"}},\"UsersStudy\":{}}}}, \"UsersJob\":{}");

		assertTrue("job positions should be empty", parsedUser
				.getJobPositions().isEmpty());
	}

	public void testParseUserWithJobPositions() throws Exception {
		final User parsedUser = userFromJsonString("{\"status\":\"ok\",\"result\":{\"status\":\"ok\",\"user\":{\"User\":{\"id\":\"1\",\"auth_token\":null,\"email\":null,\"username\":\"mostrobora\",\"name\":\"Mostro\",\"surname\":\"Bora\",\"headline\":\"Cookie monster at Sessame Street\",\"picture\":\"http://m3.licdn.com/mpr/mprx/0_oivHPfsr3BHERRh1ECncPuu23rOIUYr1eXTBPuIrOnoRkVFPQL5Xj2xOfjYq4MA0IhB9yIJuE9-D\",\"show_name\":true,\"show_headline\":true,\"show_picture\":false,\"show_skills\":true,\"show_jobs\":false,\"show_in_searches\":true},\"UsersJob\":{\"0\":{\"id\":\"8\",\"title\":\"Desarrollador\",\"is_current\":\"1\",\"user_id\":\"1\"},\"1\":{\"id\":\"9\",\"title\":\"Analista programador\",\"is_current\":\"0\",\"user_id\":\"1\"}},\"UsersSkill\":{\"0\":{\"id\":\"106288\",\"user_id\":\"1\",\"skill_id\":\"7\"},\"1\":{\"id\":\"106289\",\"user_id\":\"1\",\"skill_id\":\"15\"},\"2\":{\"id\":\"106290\",\"user_id\":\"1\",\"skill_id\":\"19\"},\"3\":{\"id\":\"106291\",\"user_id\":\"1\",\"skill_id\":\"21\"},\"4\":{\"id\":\"106292\",\"user_id\":\"1\",\"skill_id\":\"24\"},\"5\":{\"id\":\"106293\",\"user_id\":\"1\",\"skill_id\":\"31\"},\"6\":{\"id\":\"106294\",\"user_id\":\"1\",\"skill_id\":\"50\"},\"7\":{\"id\":\"106295\",\"user_id\":\"1\",\"skill_id\":\"72\"},\"8\":{\"id\":\"106296\",\"user_id\":\"1\",\"skill_id\":\"73\"},\"9\":{\"id\":\"106297\",\"user_id\":\"1\",\"skill_id\":\"74\"},\"10\":{\"id\":\"106298\",\"user_id\":\"1\",\"skill_id\":\"75\"}},\"UsersStudy\":{}}}}");

		assertEquals("job position desarrollador", //
				new JobPosition("8", "Desarrollador", true, "1"),//
				parsedUser.getJobPositions().get(0));
		assertEquals("job position analista programador", //
				new JobPosition("9", "Analista programador", false, "1"),//
				parsedUser.getJobPositions().get(1));
	}

	// ************************************************ //
	// ====== Utilities ======
	// ************************************************ //

	private User userFromJsonString(final String json) throws JSONException {
		final JSONObject jsonUserWithoutJobPositions = new JSONObject(json);
		final GetUserResponse userParser = new GetUserResponse(
				jsonUserWithoutJobPositions);
		return userParser.getUser();
	}

}
