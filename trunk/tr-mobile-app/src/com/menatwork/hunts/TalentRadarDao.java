package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.menatwork.model.ProxyUser;
import com.menatwork.model.User;
import com.menatwork.utils.StringUtils;

public class TalentRadarDao extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "talent_radar_app";
	private static final String TABLE_SIMPLE_SKILL_HUNTS = "simple_skill_hunts";
	private static final String TABLE_DEFAULT_HUNTS = "default_hunts";

	private static final String KEY_ID = "_id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_USER_IDS = "user_ids";
	private static final String KEY_REQUIRED_SKILLS = "required_skills";
	private static final String KEY_PREFERRED_SKILLS = "preferred_skills";

	private static final String STRING_SEPARATOR = ",";

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static TalentRadarDao withContext(final Context context) {
		return new TalentRadarDao(context);
	}

	protected TalentRadarDao(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ************************************************ //
	// ====== SQLiteDatabase management ======
	// ************************************************ //

	@Override
	public void onCreate(final SQLiteDatabase db) {
		final String CREATE_SIMPLE_SKILLS_TABLE = //
		/*	   */"CREATE TABLE " + TABLE_SIMPLE_SKILL_HUNTS + " (" //
				+ KEY_ID + " TEXT PRIMARY KEY," //
				+ KEY_TITLE + " TEXT," //
				+ KEY_USER_IDS + " TEXT," //
				+ KEY_REQUIRED_SKILLS + " TEXT," //
				+ KEY_PREFERRED_SKILLS + " TEXT" //
				+ ");";
		db.execSQL(CREATE_SIMPLE_SKILLS_TABLE);

		final String CREATE_DEFAULT_HUNTS_TABLE = //
		/*	   */"CREATE TABLE " + TABLE_DEFAULT_HUNTS + " (" //
				+ KEY_ID + " TEXT PRIMARY KEY," //
				+ KEY_USER_IDS + " TEXT" //
				+ ");";
		db.execSQL(CREATE_DEFAULT_HUNTS_TABLE);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIMPLE_SKILL_HUNTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFAULT_HUNTS);

		// Create tables again
		onCreate(db);
	}

	public void open() {
		getWritableDatabase().close();
	}

	// ************************************************ //
	// ====== CRUD methods ======
	// ************************************************ //
	/**
	 * Saves the state of the default hunt into database.
	 */
	public void saveDefaultHunt() {
		final DefaultHunt defaultHunt = DefaultHunt.getInstance();
		Log.i(getClass().getSimpleName(), "saving default hunt = " + defaultHunt);

		final SQLiteDatabase db = getWritableDatabase();

		// final ContentValues values = new ContentValues();
		// values.put(KEY_ID, defaultHunt.getId());
		// values.put(KEY_USER_IDS, //
		// StringUtils.concatStringsWithSep(userIdsToPersist(defaultHunt),
		// STRING_SEPARATOR));

		// db.insert(TABLE_CONTACTS, null, values);

		final String insertOrUpdateString = "INSERT OR REPLACE INTO " + TABLE_DEFAULT_HUNTS + " (" //
				+ KEY_ID + ", " + KEY_USER_IDS + ") " //
				+ "VALUES (?, ?)";

		db.execSQL(
				insertOrUpdateString,
				new String[] { defaultHunt.getId(),
						StringUtils.concatStringsWithSep(userIdsToPersist(defaultHunt), STRING_SEPARATOR) });

		db.close();
	}

	/**
	 * Loads the default hunt from the database into the singleton object
	 * referred from {@link DefaultHunt#getInstance()}
	 * 
	 * @return the default hunt
	 */
	public DefaultHunt loadDefaultHunt() {
		final SQLiteDatabase db = getReadableDatabase();

		final DefaultHunt defaultHunt = DefaultHunt.getInstance();
		final String id = defaultHunt.getId();

		final Cursor cursor = db.query(TABLE_DEFAULT_HUNTS, new String[] { KEY_USER_IDS }, KEY_ID + "=?",
				new String[] { id }, null, null, null, null);

		// if there was a default hunt previously saved
		if (cursor != null && cursor.moveToFirst()) {

			final String userIds = cursor.getString(0);
			defaultHunt.addUsers(usersFromUserIds(userIds));
		}

		cursor.close();
		Log.i(getClass().getSimpleName(), "loading default hunt");

		return defaultHunt;
	}

	public void addHunt(final SimpleSkillHunt hunt) {
		final SQLiteDatabase db = getWritableDatabase();

		final ContentValues values = new ContentValues();
		values.put(KEY_ID, hunt.getId());
		values.put(KEY_TITLE, hunt.getTitle());
		values.put(KEY_USER_IDS, //
				StringUtils.concatStringsWithSep(userIdsToPersist(hunt), STRING_SEPARATOR));
		values.put(KEY_REQUIRED_SKILLS, //
				StringUtils.concatStringsWithSep(hunt.getRequiredSkills(), STRING_SEPARATOR));
		values.put(KEY_PREFERRED_SKILLS, //
				StringUtils.concatStringsWithSep(hunt.getPreferredSkills(), STRING_SEPARATOR));

		// Inserting Row
		db.insert(TABLE_SIMPLE_SKILL_HUNTS, null, values);
		db.close();
	}

	public List<SimpleSkillHunt> getAllHunts() {

		final List<SimpleSkillHunt> hunts = new ArrayList<SimpleSkillHunt>();

		// Select All Query
		final String selectQuery = "SELECT  * FROM " + TABLE_SIMPLE_SKILL_HUNTS;

		final SQLiteDatabase db = getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				final SimpleSkillHuntBuilder builder = SimpleSkillHuntBuilder.newInstance();

				builder.setId(cursor.getString(0)).setTitle(cursor.getString(1))
						.setUsers(usersFromUserIds(cursor.getString(2).split(STRING_SEPARATOR)))
						.addRequiredSkills(parseSkillsFrom(cursor.getString(3)))
						.addPreferredSkills(parseSkillsFrom(cursor.getString(4)));

				// Adding contact to list
				hunts.add(builder.build());
			} while (cursor.moveToNext());
		}

		cursor.close();
		Log.i(getClass().getSimpleName(), "getting hunts=" + hunts);

		// return contact list
		return hunts;
	}

	private List<String> parseSkillsFrom(final String string) {
		final List<String> skills = new LinkedList<String>();

		for (final String maybeSkill : string.split(STRING_SEPARATOR))
			if (maybeSkill.length() != 0)
				skills.add(maybeSkill);

		return skills;
	}

	public void deleteHunt(final SimpleSkillHunt hunt) {
		Log.i(getClass().getSimpleName(), "deleting hunt=" + hunt);
		final SQLiteDatabase db = getWritableDatabase();

		db.delete(TABLE_SIMPLE_SKILL_HUNTS, KEY_ID + " = ?", new String[] { hunt.getId() });

		db.close();
	}

	public void saveHunts(final Collection<Hunt> hunts) {
		Log.i(getClass().getSimpleName(), "saving hunts = " + hunts);

		final SQLiteDatabase db = getWritableDatabase();

		for (final Hunt hunt : hunts) {
			if (hunt instanceof SimpleSkillHunt) {
				final SimpleSkillHunt simpleSkillHunt = (SimpleSkillHunt) hunt;

				final String insertOrUpdateString = "INSERT OR REPLACE INTO " + TABLE_SIMPLE_SKILL_HUNTS
						+ " (" + KEY_ID + ", " + KEY_USER_IDS + ", " + KEY_REQUIRED_SKILLS + ", "
						+ KEY_PREFERRED_SKILLS + ") " + "VALUES (?, ?, ?, ?)";

				db.execSQL(
						insertOrUpdateString,
						new String[] {
								hunt.getId(),
								StringUtils.concatStringsWithSep(userIdsToPersist(simpleSkillHunt),
										STRING_SEPARATOR),
								StringUtils.concatStringsWithSep(simpleSkillHunt.getRequiredSkills(),
										STRING_SEPARATOR),
								StringUtils.concatStringsWithSep(simpleSkillHunt.getPreferredSkills(),
										STRING_SEPARATOR) });
			}
		}

		db.close();
	}

	// ************************************************ //
	// ====== Mappings ======
	// ************************************************ //

	private List<String> userIdsToPersist(final Hunt hunt) {
		final List<String> userIds = new LinkedList<String>();
		for (final User user : hunt.getUsers())
			userIds.add(user.getId());
		return userIds;
	}

	private List<User> usersFromUserIds(final String... userIds) {
		final List<User> users = new LinkedList<User>();

		for (final String userId : userIds)
			users.add(ProxyUser.withId(userId));

		return users;
	}

}
