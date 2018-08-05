package com.studios.uio443.cluck.presentation.view.fragment;

import android.os.Bundle;
import android.preference.*;
import com.studios.uio443.cluck.presentation.R;

/**
 * Created by zundarik 29.07.2018
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, value) -> {
		String stringValue = value.toString();

		if (preference instanceof ListPreference) {
			// For list preferences, look up the correct display value in
			// the preference's 'entries' list.
			ListPreference listPreference = (ListPreference) preference;
			int index = listPreference.findIndexOfValue(stringValue);

			// Set the summary to reflect the new value.
			preference.setSummary(
							index >= 0
											? listPreference.getEntries()[index]
											: null);

		} else {
			// For all other preferences, set the summary to the value's
			// simple string representation.
			preference.setSummary(stringValue);
		}
		return true;
	};
	EditTextPreference editTextPreference;

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 *
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
						PreferenceManager
										.getDefaultSharedPreferences(preference.getContext())
										.getString(preference.getKey(), ""));
	}

	private static void bindPreferenceBooleanToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
						PreferenceManager
										.getDefaultSharedPreferences(preference.getContext())
										.getBoolean(preference.getKey(), true));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Метод addPreferencesFromResource прочитает файл с описанием настроек и выведет их на экран
		addPreferencesFromResource(R.xml.pref_login_pin);
		setHasOptionsMenu(true);

		bindPreferenceBooleanToValue(findPreference("login_pin_switch"));
		bindPreferenceSummaryToValue(findPreference("login_pin_name"));

		SwitchPreference switchPreference = (SwitchPreference) findPreference("login_pin_switch");
		switchPreference.setOnPreferenceChangeListener(this);
		editTextPreference = (EditTextPreference) findPreference("login_pin_name");
		switchPreference.setChecked(true);
		editTextPreference.setEnabled(false);

	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference.getKey().equals("login_pin_switch")) {
			if (newValue.equals(true)) {
				editTextPreference.setEnabled(false);
			} else {
				editTextPreference.setEnabled(true);
			}
			return true;
		}
		return false;
	}

}
