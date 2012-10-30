package com.menatwork.register;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.menatwork.LoginWithTalentRadarActivity;
import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.service.Register;
import com.menatwork.service.response.Response;
import com.menatwork.skills.SkillButtonFactory;
import com.menatwork.skills.SkillSuggestionBox;
import com.menatwork.utils.NaiveDialogClickListener;

public class SkillsActivity extends DataInputActivity {

	public static final int DIALOG_ERROR = 0;
	private Button finishButton;
	private Button cancelButton;
	private EditText headline;
	private EditText skillsInput;
	private ImageButton addSkillButton;
	private ViewGroup skillsSuggestionsContainer;
	private ViewGroup skillsContainer;
	private CheckBox headlinePublic;
	private CheckBox skillsPublic;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_skills);
		findViewElements();
		setupButtons();
		initializeTextWatchers();
		initializeContainers();
	}

	private void findViewElements() {
		finishButton = (Button) findViewById(R.id.register_skills_button_finish);
		cancelButton = (Button) findViewById(R.id.register_skills_button_cancel);
		headline = (EditText) findViewById(R.id.register_skills_headline);
		headlinePublic = (CheckBox) findViewById(R.id.register_skills_headline_public);
		skillsInput = (EditText) findViewById(R.id.register_skills_skill_input);
		addSkillButton = (ImageButton) findViewById(R.id.register_skills_button_add_skill);
		skillsSuggestionsContainer = (ViewGroup) findViewById(R.id.register_skills_layout_suggestions_skills);
		skillsContainer = (ViewGroup) findViewById(R.id.register_skills_layout_skills);
		skillsPublic = (CheckBox) findViewById(R.id.register_skills_skills_public);
	}

	private void setupButtons() {
		finishButton.setOnClickListener(new NextButtonListener());
		cancelButton.setOnClickListener(new CancelButtonListener(this));

		addSkillButton.setOnClickListener(new AddSkillOnClickListener(
				skillsContainer, skillsInput));
		addSkillButton.setEnabled(false);
	}

	@Override
	Bundle getConfiguredData() {
		final Bundle bundle = new Bundle();
		return bundle;
	}

	@Override
	protected Dialog onCreateDialog(final int id, final Bundle args) {
		final Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_ERROR:
			builder.setMessage(args.getString("message"));
			builder.setPositiveButton("OK", new NaiveDialogClickListener());
			return builder.create();
		}
		return null;
	}

	private class NextButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final Bundle bundle = getIntent().getExtras();
			bundle.putAll(getConfiguredData());
			new RegisterTask().execute(bundle);
		}
	}

	private class RegisterTask extends AsyncTask<Bundle, Void, Integer> {

		private ProgressDialog progressDialog;
		public static final int SUCCESS = 0;
		public static final int FAILURE = 1;

		// public static final int REPEATED = 2;
		// public static final int MISSING_FIELDS = 3;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(SkillsActivity.this,
					getString(R.string.register_finaldialog_title),
					getString(R.string.register_finaldialog_message), true);
		}

		@Override
		protected Integer doInBackground(final Bundle... params) {
			try {
				final Bundle bundleWithRegistrationData = params[0];
				final Register register = Register.newInstance(
						SkillsActivity.this, bundleWithRegistrationData
								.getString(RegistrationExtras.EMAIL),
						bundleWithRegistrationData
								.getString(RegistrationExtras.NICKNAME),
						bundleWithRegistrationData
								.getString(RegistrationExtras.PASSWORD),
						getNameFromFullname(bundleWithRegistrationData),
						getSurnameFromFullname(bundleWithRegistrationData),
						bundleWithRegistrationData
								.getString(RegistrationExtras.HEADLINE));
				return this.handleResponse(register.execute());
			} catch (final JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return FAILURE;
		}

		@Override
		protected void onPostExecute(final Integer result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result == SUCCESS)
				startActivity(new Intent(SkillsActivity.this,
						LoginWithTalentRadarActivity.class));
			else {
				final Bundle bundleWithMessage = new Bundle();
				String message; // should be message defined by service
				message = getString(R.string.register_errordialog_message);
				bundleWithMessage.putString("message", message);
				showDialog(DIALOG_ERROR, bundleWithMessage);
			}
		}

		private int handleResponse(final Response response) {
			Log.d("RegisterTask", "JSON Response");
			Log.d("RegisterTask", response.toString());
			return response.isSuccessful() ? SUCCESS : FAILURE;
		}

		protected String getNameFromFullname(
				final Bundle bundleWithRegistrationData) {
			final String name = bundleWithRegistrationData
					.getString(RegistrationExtras.REALNAME);

			return new StringTokenizer(name, " ").nextToken();
		}

		protected String getSurnameFromFullname(
				final Bundle bundleWithRegistrationData) {
			final String fullname = bundleWithRegistrationData
					.getString(RegistrationExtras.REALNAME);
			final String nameFromFullname = getNameFromFullname(bundleWithRegistrationData);
			return fullname.substring(nameFromFullname.length() + 1);
		}
	}

	// methods/stuff copied from newhuntactivity

	private void initializeTextWatchers() {
		this.skillsInput.addTextChangedListener(new SkillsTextWatcher(
				addSkillButton, skillsContainer, skillsSuggestionsContainer));
	}

	private void initializeContainers() {
		skillsContainer.removeAllViews();
		skillsContainer.addView(getTalentRadarApplication()
				.getSkillButtonFactory().getEmptySkillsButton(this));
	}

	private class AddSkillOnClickListener implements OnClickListener {
		private final ViewGroup destinationContainer;
		private final TextView input;

		private AddSkillOnClickListener(final ViewGroup destinationContainer,
				final TextView input) {
			super();
			this.destinationContainer = destinationContainer;
			this.input = input;
		}

		@Override
		public void onClick(final View arg0) {
			final String newSkillText = input.getText().toString();
			addSkill(newSkillText, destinationContainer,
					input.getEditableText());
		}

	}

	private class SkillOnLongClickListener implements OnLongClickListener {

		@Override
		public boolean onLongClick(final View v) {
			if (v != null) {
				final Builder builder = new AlertDialog.Builder(
						SkillsActivity.this);
				builder.setItems(new CharSequence[] { "Eliminar skill" },
						new DeleteSkillListener(v));
				builder.create().show();
				return true;
			}
			return true;
		}

		private class DeleteSkillListener implements
				DialogInterface.OnClickListener {

			private final View buttonToDelete;

			public DeleteSkillListener(final View v) {
				this.buttonToDelete = v;
			}

			@Override
			public void onClick(final DialogInterface arg0, final int arg1) {
				if (findViewInViewGroup(skillsContainer, buttonToDelete))
					removeSkillFromViewGroup(buttonToDelete, skillsContainer);
			}

			private boolean findViewInViewGroup(final ViewGroup viewGroup,
					final View view) {
				boolean found = false;
				final int childCount = viewGroup.getChildCount();
				for (int i = 0; i < childCount; i++)
					if (view.equals(viewGroup.getChildAt(i)))
						found = true;
				return found;
			}
		}
	}

	private class SkillsTextWatcher implements TextWatcher {

		private final ImageButton button;
		private final ViewGroup suggestionContainer;
		private final ViewGroup skillContainer;

		public SkillsTextWatcher(final ImageButton button,
				final ViewGroup skillContainer,
				final ViewGroup suggestionContainer) {
			this.button = button;
			this.skillContainer = skillContainer;
			this.suggestionContainer = suggestionContainer;
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			if (editable == null)
				return;
			final String inputString = editable.toString();
			final boolean inputNotEmpty = inputString.length() > 0;
			button.setEnabled(inputNotEmpty);
			if (!inputNotEmpty) {
				// if input is empty... return (I know)
				suggestionContainer.removeAllViews();
				return;
			}

			// add suggestions to suggestions-box
			final SkillSuggestionBox skillSuggestionBox = getTalentRadarApplication()
					.getSkillSuggestionBox();
			final List<String> suggestions = skillSuggestionBox
					.getSuggestionsFor(inputString);

			suggestionContainer.removeAllViews();
			for (final String suggestion : suggestions) {
				final Button suggestionButton = getTalentRadarApplication()
						.getSkillButtonFactory().getSkillButton(
								SkillsActivity.this, suggestion);
				suggestionButton
						.setOnClickListener(new SuggestionOnClickListener(
								suggestionContainer, skillContainer, editable));
				suggestionContainer.addView(suggestionButton);
			}

		}

		@Override
		public void beforeTextChanged(final CharSequence arg0, final int arg1,
				final int arg2, final int arg3) {
		}

		@Override
		public void onTextChanged(final CharSequence arg0, final int arg1,
				final int arg2, final int arg3) {
		}

	}

	private class SuggestionOnClickListener implements OnClickListener {

		private final ViewGroup suggestionContainer;
		private final ViewGroup skillContainer;
		private final Editable editable;

		public SuggestionOnClickListener(final ViewGroup suggestionContainer,
				final ViewGroup skillContainer, final Editable editable) {
			this.suggestionContainer = suggestionContainer;
			this.skillContainer = skillContainer;
			this.editable = editable;
		}

		@Override
		public void onClick(final View view) {
			if (view != null) {
				suggestionContainer.removeAllViews();
				editable.clear();
				addSkill(((Button) view).getText().toString(), skillContainer,
						editable);
			}
		}

	}

	void addSkill(final String newSkillText,
			final ViewGroup destinationContainer, final Editable input) {
		final SkillButtonFactory skillButtonFactory = getTalentRadarApplication()
				.getSkillButtonFactory();
		final Button newSkillButton = skillButtonFactory.getSkillButton(
				SkillsActivity.this, newSkillText);
		newSkillButton.setOnLongClickListener(new SkillOnLongClickListener());

		// remove empty skill button if present
		if (destinationContainer.getChildCount() == 1)
			if (destinationContainer.getChildAt(0).equals(
					skillButtonFactory
							.getEmptySkillsButton(SkillsActivity.this)))
				destinationContainer.removeAllViews();

		destinationContainer.addView(newSkillButton);
		input.clear();
	}

	protected void removeSkillFromViewGroup(final View buttonToDelete,
			final ViewGroup skillsContainer) {
		skillsContainer.removeView(buttonToDelete);
		if (skillsContainer.getChildCount() == 0)
			skillsContainer.addView(getTalentRadarApplication()
					.getSkillButtonFactory().getEmptySkillsButton(this));
	}

	private TalentRadarApplication getTalentRadarApplication() {
		return TalentRadarApplication.getContext();
	}

}
