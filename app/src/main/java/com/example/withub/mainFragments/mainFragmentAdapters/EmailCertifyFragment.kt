package com.example.withub.mainFragments.mainFragmentAdapters

import android.app.Activity
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
import com.example.withub.R
import com.example.withub.SignupActivity

class EmailCertifyFragment:Fragment() {
    lateinit var userEmail: String
    lateinit var count: CountDownTimer
    lateinit var select: String
    lateinit var timer: Timer
    private var running = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.emailcertify_fragment, container, false)
        val spinner: Spinner = view.findViewById(R.id.email_spinner_signup)
        var emailText = view.findViewById<EditText>(R.id.email_edittext_signup)
        var certificationBtn = view.findViewById<Button>(R.id.email_certification_btn)
        var timerTime = view.findViewById<TextView>(R.id.timer_text_signup)
        var confirmBtn = view.findViewById<Button>(R.id.certi_num_confirm_btn_signup)
        var signupActivity = activity as SignupActivity
        var signupBackBtn = signupActivity.findViewById<Button>(R.id.signup_back_btn)
        var nextBtn = view.findViewById<Button>(R.id.next_btn_emailcertify)


        signupBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, IdPwInputFragment())
                .commit()
        }
        nextBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, NickNameAreaSelectFragment())
                .commit()
        }

        certificationBtn.setOnClickListener {
            if (select == "--선택--") {
                Log.d("message", "이메일을 도메인을 선택하세요.")
            } else {
                certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                certificationBtn.setTextColor(ContextCompat.getColor(requireContext(),R.color.thick_gray))
                certificationBtn.setEnabled(false)
                sendMailApi()  //api로 메일 보내기
                if (running) {
                    count.cancel()
                }
                timerStart(timerTime)
            }
        }

        confirmBtn.setOnClickListener{
            if (running == false) {
                Log.d("ss", "다시 인증해 주세요")
            } else {
                //인증번호 맞는지 확인
                //맞으면 nextBtn 활성화
            }
        }

        emailText(emailText, certificationBtn, timerTime, confirmBtn)
        emailSpinnerSelect(spinner, certificationBtn, timerTime, emailText, confirmBtn)
        return view
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

    fun emailSpinnerSelect(
        spinner: Spinner,
        certificationBtn: Button,
        timerTime: TextView,
        emailText: EditText,
        confirmBtn: Button
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

    fun emailText(
        emailText: EditText,
        certificationBtn: Button,
        timerTime: TextView,
        confirmBtn: Button
    ) {
        emailText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (emailText.getText().toString().length >= 1) {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_btn)
                    certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    certificationBtn.setEnabled(true)
//                    certificationBtn.setOnClickListener{
//                        certificationBtnClick(certificationBtn,timerTime,confirmBtn)
//                    }
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
                certificationBtn.setBackgroundResource(R.drawable.stroke_btn)
                certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                certificationBtn.setEnabled(true)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }


        fun sendMailApi() {
            //userEmail 전역변수 보내기 api로
            // certiNumConfirm(인증번호) 인증번호 전역변수 만들거나 하셈
        }

        fun certiNumConfirm(confirmBtn: Button) {
            //api에서 인증번호 가져와서 인증번호 확인하고 확인되면
            //다음페이지 이동
            //타이머가 0이면 다이얼로그 인증번호입력을 초과했습니다 다시 인증해 주세요. 인증번호 틀리면 인증 번호를 다시 보내주세요
        }


}
