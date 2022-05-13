package com.example.withub

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {
    lateinit var areaSelect: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.setting_activity)
        val areaSelectSpinner = findViewById<Spinner>(R.id.area_select_spinner_setting)

        areaSpinnerSelect(areaSelectSpinner)

    }

    fun areaSpinnerSelect(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.area_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                areaSelect = spinner.selectedItem.toString()

            }
        }
    }
}