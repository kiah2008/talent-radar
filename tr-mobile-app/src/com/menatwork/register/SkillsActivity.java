package com.menatwork.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.menatwork.LoginActivity;
import com.menatwork.MainActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class SkillsActivity extends DataInputActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_skills);
		setupButtons();
	}

	private void setupButtons() {
		Button finishButton = (Button) findViewById(R.id.register_skills_button_finish);
		Button cancelButton = (Button) findViewById(R.id.register_skills_button_cancel);

		finishButton.setOnClickListener(new StartActivityOnClickListener(this,
				MainActivity.class));
		cancelButton.setOnClickListener(new StartActivityOnClickListener(this,
				LoginActivity.class));
	}

	@Override
	Bundle getConfiguredData() {
		Bundle bundle = new Bundle();
		bundle.putString(RegistrationExtras.HEADLINE, getControlHeadline()
				.getText().toString());
		return bundle;
	}

	private TextView getControlHeadline() {
		return (TextView) findViewById(R.id.register_skills_headline);
	}
}
