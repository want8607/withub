package com.example.withub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ResignAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.resign_activity)
    }
}