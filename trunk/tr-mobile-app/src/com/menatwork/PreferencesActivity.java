package com.menatwork;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.menatwork.preferences.TalentRadarPreferences;

// TODO - agregar en las etiquetas que son 'Segundos' de actualización - 30/08/2012
public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	// private Button saveChangesButton;
	// private Button resetChangesButton;
	//
	// private ToggleButton networkActivationToggleButton;
	// private ToggleButton gpsActivationToggleButton;
	//
	// private EditText actualizationDurationTextEdit;
	// private EditText actualizationFrequencyTextEdit;
	//
	// private EditText pingMessageTextEdit;
	//
	// private TalentRadarPreferences preferences;

	// @Override
	// protected void findViewElements() {
	// saveChangesButton = findButtonById(R.id.preferences_save_changes_button);
	// resetChangesButton =
	// findButtonById(R.id.preferences_reset_changes_button);
	//
	// networkActivationToggleButton =
	// findToggleButtonById(R.id.preferences_network_activation_toggle_button);
	// gpsActivationToggleButton =
	// findToggleButtonById(R.id.preferences_gps_activation_toggle_button);
	//
	// actualizationDurationTextEdit =
	// findEditTextById(R.id.preferences_actualization_duration_text_edit);
	// actualizationFrequencyTextEdit =
	// findEditTextById(R.id.preferences_actualization_frequency_text_edit);
	//
	// pingMessageTextEdit =
	// findEditTextById(R.id.preferences_ping_message_text_edit);
	// }
	//
	// @Override
	// protected void setupButtons() {
	// saveChangesButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(final View v) {
	// saveChanges();
	// }
	// });
	// resetChangesButton.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(final View v) {
	// resetChanges();
	// }
	// });
	// }
	//
	// @Override
	// protected void postCreate(final Bundle savedInstanceState) {
	// super.postCreate(savedInstanceState);
	//
	// preferences = getTalentRadarApplication().getPreferences();
	//
	// initializeValues();
	// }
	//
	// protected void resetChanges() {
	// preferences.discardChanges();
	// initializeValues();
	// }
	//
	// protected void saveChanges() {
	// preferences.setNewEdition();
	//
	// preferences.setNetworkLocationActivation(networkActivationToggleButton
	// .isChecked());
	// preferences.setGpsLocationActivation(gpsActivationToggleButton
	// .isChecked());
	//
	// preferences.setActualizationDurationSeconds(Long
	// .valueOf(actualizationDurationTextEdit.getText().toString()));
	// preferences.setActualizationFrequencySeconds(Long
	// .valueOf(actualizationFrequencyTextEdit.getText().toString()));
	//
	// preferences.setPingMessage(pingMessageTextEdit.getText().toString());
	//
	// preferences.commitChanges();
	// }
	//
	// @Override
	// protected int getViewLayoutId() {
	// return R.layout.preferences;
	// }
	//
	// private void initializeValues() {
	// networkActivationToggleButton.setChecked(preferences
	// .isNetworkLocationActivation());
	// gpsActivationToggleButton.setChecked(preferences
	// .isGpsLocationActivation());
	//
	// actualizationDurationTextEdit.setText(String.valueOf(preferences
	// .getActualizationDurationSeconds()));
	// actualizationFrequencyTextEdit.setText(String.valueOf(preferences
	// .getActualizationFrequencySeconds()));
	//
	// pingMessageTextEdit.setText(preferences.getPingMessage());
	// }
}
