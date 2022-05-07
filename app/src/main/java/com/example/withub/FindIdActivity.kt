package com.example.withub

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.withub.mainFragments.FindIdEmailValue
import com.example.withub.mainFragments.FindIdSendEmailApi
import com.example.withub.mainFragments.IdFindEmailCheckData
import com.example.withub.mainFragments.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class FindIdActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var count: CountDownTimer
    lateinit var select: String
    lateinit var token: String
    private var running = false
    private var confirmBtnBoolean = false
    val retrofit = RetrofitClient.initRetrofit()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WITHUB)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findid_activity)

        val spinner: Spinner = findViewById(R.id.email_spinner_find_id)
        val emailText = findViewById<EditText>(R.id.email_edittext_find_id)
        val findIdBackBtn = findViewById<Button>(R.id.find_id_back_btn)
        val certificationBtn = findViewById<Button>(R.id.email_certification_btn_find_id)
        var timerTime = findViewById<TextView>(R.id.timer_text_find_id)

        findIdBackBtn.setOnClickListener{
            if (running == true) {
                count.cancel()
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        certificationBtn.setOnClickListener{
            confirmBtnBoolean = true
            if (select == "--선택--") {
                dialogMessage("도메인을 선택해주세요.")
            } else {
                certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                certificationBtn.setTextColor(ContextCompat.getColor(this,R.color.thick_gray))
                certificationBtn.setEnabled(false)
                sendMailApi()  //api로 메일 보내기
                if (running) {
                    count.cancel()
                }
                timerStart(timerTime)
            }
        }

        emailSpinnerSelect(spinner,emailText)
        emailRegEx(emailText,certificationBtn)
    }

    fun sendMailApi() {
        var inform = FindIdEmailValue(userEmail)
        val requestFindIdSendEmailApi = retrofit.create(FindIdSendEmailApi::class.java)
        requestFindIdSendEmailApi.emailCheck(inform).enqueue(object : retrofit2.Callback<IdFindEmailCheckData> {
            override fun onFailure(
                call: Call<IdFindEmailCheckData>,
                t: Throwable
            ) {
            }
            override fun onResponse(call: Call<IdFindEmailCheckData>, response: Response<IdFindEmailCheckData>) {
                token = response.body()!!.token
            }
        })
    }

    fun emailSpinnerSelect(
        spinner: Spinner,
        emailText: EditText,
    ) {
        ArrayAdapter.createFromResource(
            this,
            R.array.email_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                select = spinner.selectedItem.toString()
                userEmail = emailText.getText().toString() + "@" + select
            }
        }
    }

    fun emailRegEx(
        emailText: EditText,
        certificationBtn: Button
    ) {
        emailText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (emailText.getText().toString().isNotEmpty()) {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_btn)
                    certificationBtn.setTextColor(ContextCompat.getColor(this@FindIdActivity, R.color.black))
                    certificationBtn.setEnabled(true)
                } else {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    certificationBtn.setTextColor(
                        ContextCompat.getColor(
                            this@FindIdActivity,
                            R.color.thick_gray
                        )
                    )
                    certificationBtn.setEnabled(false)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun timerStart(timerTime: TextView) {
        running = true
        count = object : CountDownTimer(1000 * 300, 1000) {
            override fun onTick(p0: Long) {
                Log.d("ss", "$p0")
                var minuteTime = (p0 / 1000 / 60).toString()
                var secondsTime = (p0 / 1000 % 60).toString()
                if (secondsTime.length == 1) {
                    timerTime.text = "$minuteTime:0$secondsTime"
                } else {
                    timerTime.text = "$minuteTime:$secondsTime"
                }
            }
            override fun onFinish() {
                running = false
            }
        }
        count.start()
    }


    fun dialogMessage(message:String) {
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("확인", null)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}

