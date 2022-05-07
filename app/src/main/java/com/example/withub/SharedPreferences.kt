package com.example.withub

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences (context:Context){
    val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token",null)
        set(value){
            prefs.edit().putString("token",value).apply()
        }
}