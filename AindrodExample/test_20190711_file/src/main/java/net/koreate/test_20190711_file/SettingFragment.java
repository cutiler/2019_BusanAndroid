package net.koreate.test_20190711_file;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingFragment  extends PreferenceFragment {

    SharedPreferences prefs;

    ListPreference soundPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        soundPreference = (ListPreference)findPreference("sound_list");

        if(!prefs.getString("sound_list","").equals("")){
            soundPreference.setSummary(prefs.getString("sound_list","카톡"));
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("sound_list")){
                soundPreference.setSummary(prefs.getString(key,"카톡"));
            }
        }
    };



}
