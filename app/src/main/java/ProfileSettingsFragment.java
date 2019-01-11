import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import tech.rtsproduction.tuktuk.R;

public class ProfileSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.profile_pref);
        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        setPreferenceSummary(findPreference(getString(R.string.pref_name)));
        setPreferenceSummary(findPreference(getString(R.string.pref_mobile)));
        setPreferenceSummary(findPreference(getString(R.string.pref_email)));
    }

    private void setPreferenceSummary(Preference p) {
        if (p instanceof EditTextPreference) {
            String value = sharedPreferences.getString(p.getKey(),"");
            p.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (preference instanceof EditTextPreference) {
                setPreferenceSummary(preference);
            }
        }
    }
}
