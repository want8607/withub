package com.example.withub.loginFragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.withub.*
import retrofit2.Call
import retrofit2.Response

class PwCertifyFragment:Fragment() {
    lateinit var select: String
    lateinit var userEmail: String
    lateinit var count: CountDownTimer
    lateinit var token: String
    private var running = false
    private var confirmBtnBoolean = false
    val retrofit = RetrofitClient.initRetrofit()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.pwcertify_fragment, container, false)
        val findPwActivity = activity as FindPwActivity
        val findPwBackBtn = findPwActivity.findViewById<Button>(R.id.find_pw_back_btn)
        val spinner = view.findViewById<Spinner>(R.id.email_spinner_find_pw)
        val emailText = view.findViewById<EditText>(R.id.email_edittext_find_pw)
        val certificationBtn = view.findViewById<Button>(R.id.email_certification_btn_find_pw)
        val timerTime = view.findViewById<TextView>(R.id.timer_text_find_pw)
        val idText = view.findViewById<EditText>(R.id.id_edittext_find_pw)

        findPwBackBtn.setOnClickListener{
            val intent = Intent(findPwActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        certificationBtn.setOnClickListener {
            confirmBtnBoolean = true

            if (idText.text.toString().isEmpty()) {
                dialogMessage("아이디를 적어주세요.")
            } else if (select == "--선택--") {
                dialogMessage("도메인을 선택해주세요.")
            } else {
                certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                certificationBtn.setTextColor(ContextCompat.getColor(requireContext(),R.color.thick_gray))
                certificationBtn.setEnabled(false)
                sendMailApi(idText.text.toString())  //api로 메일 보내기
                if (running) {
                    count.cancel()
                }
                timerStart(timerTime)
            }
        }

        idRegEx(idText,emailText,certificationBtn)
        emailRegEx(emailText, certificationBtn)
        emailSpinnerSelect(spinner, emailText)
        return view
    }

    fun emailSpinnerSelect(
        spinner: Spinner,
        emailText: EditText,
    ) {
        ArrayAdapter.createFromResource(
            requireContext(),
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
                if (emailText.getText().toString().length >= 1) {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_btn)
                    certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    certificationBtn.setEnabled(true)
                } else {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    certificationBtn.setTextColor(
                        ContextCompat.getColor(
                            context!!,
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

    fun idRegEx(idText: EditText,emailText: EditText,certificationBtn: Button) {
        idText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (emailText.getText().toString().length >= 1) {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_btn)
                    certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    certificationBtn.setEnabled(true)
                } else {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    certificationBtn.setTextColor(
                        ContextCompat.getColor(
                            context!!,
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

    fun sendMailApi(idText: String) {
        var inform = FindPwIdEmailValue(idText,userEmail)
        val requestSendEmailApi = retrofit.create(FindPwSendEmailApi::class.java)
        Log.d("message","sdfsdfsdf")

        requestSendEmailApi.idEmailCheck(inform).enqueue(object : retrofit2.Callback<FindPwIdEmailCheckData> {
            override fun onFailure(
                call: Call<FindPwIdEmailCheckData>,
                t: Throwable
            ) {
                Log.d("message","sdfsdfsdf")

            }
            override fun onResponse(call: Call<FindPwIdEmailCheckData>, response: Response<FindPwIdEmailCheckData>) {
                Log.d("message","${response.body()!!.success}")
                Log.d("message","${response.body()!!.token}")

                token = response.body()!!.token
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
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton("확인", null)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}