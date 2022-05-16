package com.example.withub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AccountActivity : AppCompatActivity() {
    lateinit var areaSelect : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.account_activity)
        val areaSelectSpinner = findViewById<Spinner>(R.id.area_select_spinner_account)

        areaSpinnerSelect(areaSelectSpinner)

        //비밀번호 변경
        findViewById<LinearLayout>(R.id.account_activity_change_password_view).setOnClickListener {
            val intent = Intent(applicationContext, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        //회원탈퇴
        findViewById<LinearLayout>(R.id.account_activity_delete_view).setOnClickListener {
            val intent = Intent(applicationContext, ResignAccount::class.java)
            startActivity(intent)
        }
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