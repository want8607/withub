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
import com.example.withub.TermsOfUseFragment
import java.util.regex.Pattern


class EmailCertifyFragment:Fragment() {
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

        emailSpinnerSelect(spinner,certificationBtn,timerTime,emailText,confirmBtn)
        emailText(emailText,certificationBtn)


        signupBackBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, IdPwInputFragment()).commit()
        }

        return view
    }
    fun emailSpinnerSelect(spinner: Spinner,certificationBtn:Button,timerTime: TextView,emailText:EditText,confirmBtn:Button) {
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
                var domain = spinner.selectedItem.toString()
                    certificationBtn.setOnClickListener{
                        if (domain == "--선택--") {
                            Log.d("message", "이메일을 도메인을 선택하세요.")
                        } else {
                            certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                            certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.thick_gray))
                            certificationBtn.setEnabled(false)
                            var userEmail : String
                            userEmail = emailText.getText().toString() + "@" + domain
                            sendMailApi(userEmail)
                            certificationBtnClick(certificationBtn,timerTime,confirmBtn)
                        }
                    }
            }
        }
    }

    fun emailText(emailText:EditText,certificationBtn:Button) {
        emailText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (emailText.getText().toString().length >= 1) {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_btn)
                    certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    certificationBtn.setEnabled(true)
                    certificationBtn.setOnClickListener{
                        certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                        certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.thick_gray))
                        certificationBtn.setEnabled(false)
                    }
                } else {
                    certificationBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    certificationBtn.setTextColor(ContextCompat.getColor(context!!, R.color.thick_gray))
                    certificationBtn.setEnabled(false)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun certificationBtnClick(certificationBtn: Button,timerTime: TextView,confirmBtn:Button){
//        certificationBtn.setOnClickListener{
            startCountDown(timerTime,certificationBtn,confirmBtn)

    }


    fun startCountDown(timerTime:TextView,certificationBtn: Button,confirmBtn:Button) {
        var countDownTimer = object : CountDownTimer(1000 * 300, 1000) {
            override fun onTick(p0: Long) {
                certificationBtn.setOnClickListener{
                    Log.d("sss", "can")
                    onFinish()
                }
                // countDownInterval 마다 호출 (여기선 1000ms)
                var secondsTime = (p0 / 1000%60).toString()
                Log.d("sss", "${secondsTime}")
                if (secondsTime.length == 1) {
                    timerTime.setText((p0 / 1000/60).toString()+":"+"0"+(p0 / 1000%60))
                }else {
                    timerTime.setText((p0 / 1000/60).toString()+":"+(p0 / 1000%60))
                }
            }
            override fun onFinish() {
                // 타이머가 종료되면 호출
                cancel()
                start()
//                timerTime.setText("0:00")
                certificationBtnClick(certificationBtn,timerTime,confirmBtn)
            }
        }.start()
    }

    fun sendMailApi(userEmail:String) {

    }



}