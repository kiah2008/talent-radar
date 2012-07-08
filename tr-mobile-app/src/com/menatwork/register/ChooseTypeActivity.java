package com.menatwork.register;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.menatwork.R;
import com.menatwork.utils.StartActivityListener;

public class ChooseTypeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_choose_type);
		setupButtons();
	}

	private void setupButtons() {
		Button linkedinButton = (Button) findViewById(R.id.register_type_button_linkedin);
		Button normalButton = (Button) findViewById(R.id.register_type_button_normal);

		linkedinButton.setEnabled(false);
		normalButton.setOnClickListener(new StartActivityListener(this,
				EssentialsActivity.class));
	}

}
