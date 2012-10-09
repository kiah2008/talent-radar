package com.menatwork;

import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public abstract class TransactionalSharedPreferencesEditor {

	protected TransactionalSharedPreferencesEditor(
			SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	private final SharedPreferences sharedPreferences;

	private Editor editor;
	private Set<String> keysChanged;

	// ************************************************ //
	// ====== Edition Session ======
	// ************************************************ //

	public void beginNewEdition() {
		editor = sharedPreferences.edit();
		keysChanged = new HashSet<String>();
	}

	public void commitChanges() {
		final boolean commitSuccessful = editor.commit();

		if (!commitSuccessful)
			throw new RuntimeException("could not save changes to preferences");

		notifyChanges(keysChanged.toArray(new String[0]));
		discardChanges();
	}

	public void discardChanges() {
		editor = null;
		keysChanged = null;
	}

	protected abstract void notifyChanges(final String... keys);

	protected boolean getBoolean(String key, boolean defValue) {
		return sharedPreferences.getBoolean(key, defValue);
	}

	protected float getFloat(String key, float defValue) {
		return sharedPreferences.getFloat(key, defValue);
	}

	protected int getInt(String key, int defValue) {
		return sharedPreferences.getInt(key, defValue);
	}

	protected long getLong(String key, long defValue) {
		return sharedPreferences.getLong(key, defValue);
	}

	protected String getString(String key, String defValue) {
		return sharedPreferences.getString(key, defValue);
	}

	protected void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
	}

	protected void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
	}

	protected Editor putBoolean(String key, boolean value) {
		keysChanged.add(key);
		return editor.putBoolean(key, value);
	}

	protected Editor putFloat(String key, float value) {
		keysChanged.add(key);
		return editor.putFloat(key, value);
	}

	protected Editor putInt(String key, int value) {
		keysChanged.add(key);
		return editor.putInt(key, value);
	}

	protected Editor putLong(String key, long value) {
		keysChanged.add(key);
		return editor.putLong(key, value);
	}

	protected Editor putString(String key, String value) {
		keysChanged.add(key);
		return editor.putString(key, value);
	}

}
