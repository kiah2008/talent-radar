package com.menatwork.register;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.menatwork.R;
import com.menatwork.utils.StartActivityListener;

public class ChooseTypeActivity extends Activity {
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_choose_type);
		setupButtons();
	}

	private void setupButtons() {
		final Button linkedinButton = (Button) findViewById(R.id.register_type_button_linkedin);
		final Button normalButton = (Button) findViewById(R.id.register_type_button_normal);

		linkedinButton.setEnabled(false);
		normalButton.setOnClickListener(new StartActivityListener(this,
				EssentialsActivity.class));
	}

}
