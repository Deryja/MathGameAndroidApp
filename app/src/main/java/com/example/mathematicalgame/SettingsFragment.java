package com.example.mathematicalgame;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;


//For preferences
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String
            rootKey) {
        setPreferencesFromResource(R.xml.pref, rootKey);
    }
}
