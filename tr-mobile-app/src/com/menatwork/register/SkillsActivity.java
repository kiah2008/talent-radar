package com.menatwork.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.menatwork.MainActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class SkillsActivity extends DataInputActivity {

	private Button finishButton;
	private Button cancelButton;
	private EditText headline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_skills);
		findViewElements();
		setupButtons();
	}

	private void findViewElements() {
		finishButton = (Button) findViewById(R.id.register_skills_button_finish);
		cancelButton = (Button) findViewById(R.id.register_skills_button_cancel);
		headline = (EditText) findViewById(R.id.register_skills_headline);
	}

	private void setupButtons() {
		finishButton.setOnClickListener(this.getNextButtonClickListener());
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	@Override
	Bundle getConfiguredData() {
		Bundle bundle = new Bundle();
		bundle.putString(RegistrationExtras.HEADLINE, getControlHeadline()
				.getText().toString());
		return bundle;
	}

	private EditText getControlHeadline() {
		return headline;
	}

	private StartActivityOnClickListener getNextButtonClickListener() {
		return new StartActivityOnClickListener(this, MainActivity.class) {

			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				bundle.putAll(getConfiguredData());
				new RegisterTask(SkillsActivity.this).execute(bundle);
				super.onClick(v);
			}

		};
	}
}
