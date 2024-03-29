package com.wh.mysitter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.shared_preference_setting, rootKey);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            switch (preference.getKey()){
                case "setting_about_me_github":
                case "setting_about_me_coolapk":
                case "setting_about_goto_app_coolapk":
                    String addr = String.valueOf(preference.getSummary());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(addr));
                    startActivity(intent);
                    break;
                case "tmp_task_list":
                    Intent intent1 = new Intent(requireContext(),TaskListActivity.class);
                    startActivity(intent1);
            }
            return super.onPreferenceTreeClick(preference);
        }
    }
}