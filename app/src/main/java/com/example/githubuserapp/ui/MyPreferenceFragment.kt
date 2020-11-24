package com.example.githubuserapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubuserapp.R
import com.example.githubuserapp.utils.AlarmReceiver

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var ON: String
    private lateinit var isOnReminderPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {
        ON = resources.getString(R.string.reminder)
        isOnReminderPreference = findPreference<SwitchPreference>(ON) as SwitchPreference
        alarmReceiver = AlarmReceiver()

        if (isOnReminderPreference.isChecked) {
            alarmReceiver.setRepeatingAlarm(
                context
            )
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isOnReminderPreference.isChecked = sh.getBoolean(ON, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == ON) {
            if (isOnReminderPreference.isChecked) {
                alarmReceiver.setRepeatingAlarm(
                    context
                )
            } else if (!isOnReminderPreference.isChecked) {
                alarmReceiver.cancelAlarm(context)
            }
        }
    }
}