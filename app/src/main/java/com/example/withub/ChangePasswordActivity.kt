package com.example.withub

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    val retrofit = RetrofitClient.initRetrofit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.point_color)
        setContentView(R.layout.change_password_activity)

        val backBtn = findViewById<Button>(R.id.back_btn_change_password)
        val confirmBtn = findViewById<Button>(R.id.confirm_btn_change_password)
        val pwText = findViewById<EditText>(R.id.current_pw_edittext_change_password)

        backBtn.setOnClickListener{
            val intent = Intent(applicationContext, AccountActivity::class.java)
            startActivity(intent)
        }

        confirmBtn.setOnClickListener{
            currentPwConfirmApi(pwText)
        }
    }

    fun currentPwConfirmApi(pwText:EditText){
        val requestCurrentPwConfirmApi = retrofit.create(CurrentPwConfirmApi::class.java)
        requestCurrentPwConfirmApi.tokenPwCheck(MyApp.prefs.accountToken!!,pwText.text.toString()).enqueue(object : retrofit2.Callback<PwCheckData> {
            override fun onFailure(
                call: Call<PwCheckData>,
                t: Throwable
            ) {
            }
            override fun onResponse(call: Call<PwCheckData>, response: Response<PwCheckData>) {
                if (response.body()!!.success) {
                    dialogMessage("비밀번호가 확인되었습니다.")
                } else {
                    dialogMessage("비밀번호가 틀렸습니다.")
                }
            }
        })
    }

    fun dialogMessage(message:String) {
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("확인", null)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}