package com.menatwork.register;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.menatwork.LoginActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class RegisterEssentialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_essential);
		setupButtons();
	}

	private void setupButtons() {
		Button nextButton = (Button) findViewById(R.id.register_button_next);
		Button cancelButton = (Button) findViewById(R.id.register_button_cancel);

		nextButton.setOnClickListener(new StartActivityOnClickListener(this,
				RegisterSkillsActivity.class));
		cancelButton.setOnClickListener(new StartActivityOnClickListener(this,
				LoginActivity.class));

	}

}
