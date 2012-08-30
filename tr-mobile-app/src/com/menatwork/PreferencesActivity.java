package com.menatwork;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.menatwork.preferences.TalentRadarPreferences;

// TODO - 1) hacer que se guarden las configs, 2) hacer que el resto del universo las use - miguel, 3) agregar en las etiquetas que son 'Segundos' de actualización - 30/08/2012
public class PreferencesActivity extends GuiTalentRadarActivity {

	private Button saveChangesButton;
	private Button resetChangesButton;
	private ToggleButton networkActivationToggleButton;
	private ToggleButton gpsActivationToggleButton;
	private EditText actualizationDurationTextEdit;
	private EditText actualizationFrequencyTextEdit;

	@Override
	protected void findViewElements() {
		saveChangesButton = findButtonById(R.id.preferences_save_changes_button);
		resetChangesButton = findButtonById(R.id.preferences_reset_changes_button);
		networkActivationToggleButton = findToggleButtonById(R.id.preferences_network_activation_toggle_button);
		gpsActivationToggleButton = findToggleButtonById(R.id.preferences_gps_activation_toggle_button);
		actualizationDurationTextEdit = findEditTextById(R.id.preferences_actualization_duration_text_edit);
		actualizationFrequencyTextEdit = findEditTextById(R.id.preferences_actualization_frequency_text_edit);
	}

	@Override
	protected void setupButtons() {
		saveChangesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				saveChanges();
			}
		});
		resetChangesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				resetChanges();
			}
		});
	}

	protected void resetChanges() {
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException(
				"PreferencesActivity.resetChanges");
		// reset changes -> should take what's persisted and reload settings
		// screen
	}

	protected void saveChanges() {
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException(
				"PreferencesActivity.saveChanges");
		// save changes -> should take what's in screen and persist it (also
		// notify about the update)

		// para guardar en el archivo de preferences
		// final SharedPreferences.Editor editor = preferences.edit();
		// editor.putInt("storedInt", storedPreference); // value to store
		// editor.commit();
		// Editor also support methods like remove() and clear() to delete the
		// preference value from the file.
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.preferences;
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		initializeValues();
	}

	private void initializeValues() {
		final TalentRadarPreferences preferences = getTalentRadarApplication()
				.getPreferences();

		networkActivationToggleButton.setChecked(preferences
				.getNetworkLocationActivation());
		gpsActivationToggleButton.setChecked(preferences
				.getGpsLocationActivation());

		actualizationDurationTextEdit.setText(String.valueOf(preferences
				.getActualizationDurationSeconds()));
		actualizationFrequencyTextEdit.setText(String.valueOf(preferences
				.getActualizationFrequencySeconds()));
	}
}
