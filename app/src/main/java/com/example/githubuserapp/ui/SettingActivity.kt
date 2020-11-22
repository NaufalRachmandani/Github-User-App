package com.example.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_setting, MyPreferenceFragment()).commit()
    }
}