package com.menatwork;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class SettingsActivity extends GuiTalentRadarActivity {

	private Button saveChangesButton;
	private Button resetChangesButton;
	private ToggleButton networkActivationToggleButton;
	private ToggleButton gpsActivationToggleButton;
	private EditText actualizationDurationTextEdit;
	private EditText actualizationFrequencyTextEdit;

	@Override
	protected void findViewElements() {
		saveChangesButton = findButtonById(R.id.settings_save_changes_button);
		resetChangesButton = findButtonById(R.id.settings_reset_changes_button);
		networkActivationToggleButton = findToggleButtonById(R.id.settings_network_activation_toggle_button);
		gpsActivationToggleButton = findToggleButtonById(R.id.settings_gps_activation_toggle_button);
		actualizationDurationTextEdit = findEditTextById(R.id.settings_actualization_duration_text_edit);
		actualizationFrequencyTextEdit = findEditTextById(R.id.settings_actualization_frequency_text_edit);
	}

	@Override
	protected void setupButtons() {
		// TODO Auto-generated constructor stub
		// save changes -> should take what's in screen and persist it (also
		// notify about the update)
		// reset changes -> should take what's persisted and reload settings
		// screen
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.settings;
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		// TODO - load everything from persisted - boris - 30/08/2012
	}
}
