package com.menatwork.view;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class LongEditTextPreference extends EditTextPreference {

	public LongEditTextPreference(final Context context) {
		super(context);
	}

	public LongEditTextPreference(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);
	}

	public LongEditTextPreference(final Context context,
			final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected String getPersistedString(final String defaultReturnValue) {
		return String
				.valueOf(getPersistedLong(valueOrZero(defaultReturnValue)));
	}

	private Long valueOrZero(final String defaultReturnValue) {
		return Long.valueOf(defaultReturnValue == null ? "0"
				: defaultReturnValue);
	}

	@Override
	protected boolean persistString(final String value) {
		return persistLong(valueOrZero(value));
	}
}