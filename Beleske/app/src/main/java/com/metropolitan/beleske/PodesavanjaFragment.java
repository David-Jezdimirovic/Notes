package com.metropolitan.beleske;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class PodesavanjaFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }
}
