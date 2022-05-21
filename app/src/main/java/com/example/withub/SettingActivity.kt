package com.example.withub

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {
    lateinit var areaSelect: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.setting_activity)
        val backBtn = findViewById<Button>(R.id.back_btn_setting)

        backBtn.setOnClickListener{
            finish()
        }


    }


}