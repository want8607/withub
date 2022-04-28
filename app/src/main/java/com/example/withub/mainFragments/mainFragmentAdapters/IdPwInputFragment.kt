package com.example.withub.mainFragments.mainFragmentAdapters

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.withub.LoginActivity
import com.example.withub.R
import com.example.withub.SignupActivity
import com.example.withub.TermsOfUseFragment
import java.util.regex.Pattern

class IdPwInputFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.idpwinput_fragment, container, false)
        var signupActivity = activity as SignupActivity
        var signupBackBtn = signupActivity.findViewById<Button>(R.id.signup_back_btn)
        var idText = view.findViewById<EditText>(R.id.id_edittext_signup)
        var idCheckView = view.findViewById<TextView>(R.id.id_check_textview_signup)
        var idDuplicateBtn = view.findViewById<Button>(R.id.id_duplicate_check_btn_signup)
        var pwText = view.findViewById<EditText>(R.id.pw_edittext_signup)
        var pwCheckView = view.findViewById<TextView>(R.id.pw_check_textview_signup)
        var safetyView = view.findViewById<TextView>(R.id.pw_safety_textview_signup)
        var pwConfirmText = view.findViewById<EditText>(R.id.pw_confirm_edittext_signup)
        var pwMismatchView = view.findViewById<TextView>(R.id.pw_confirm_mismatch_textview_signup)

        signupBackBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, TermsOfUseFragment()).commit()
        }

        idRegExp(idText,idCheckView,idDuplicateBtn)
        pwRegExp(view,pwText,pwCheckView,safetyView)
        pwConfirmRegExp(view,pwText,pwConfirmText,pwMismatchView)

        return view
    }

    fun idRegExp(idText: EditText,idCheckView:TextView,idDuplicateBtn:Button) {
        idText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("mmmmmssss","sdfsdf")
                var boolean = Pattern.matches("^[a-z0-9]*$", idText.getText().toString())
                if (boolean==false || idText.getText().length < 5) {
                    idCheckView.setText("5~15자 영문(소문자), 숫자만 사용하세요.")
                    idDuplicateBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    idDuplicateBtn.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
                    idDuplicateBtn.setEnabled(false)
                } else {
                    idCheckView.setText("")
                    idDuplicateBtn.setBackgroundResource(R.drawable.stroke_btn)
                    idDuplicateBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    idDuplicateCheck(idDuplicateBtn)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun pwRegExp(view:View,pwText:EditText,pwCheckView:TextView,safetyView:TextView) {
        pwText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var numSpecialBoolean = Pattern.matches("^.*(?=^.{8,20}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$", pwText.getText().toString())
                if (numSpecialBoolean == false ) {
                    pwCheckView.setText("8~20자 영문, 숫자, 특수문자를 사용하세요.")
                    safetyView.setText("사용 불가")
                    nextBtnActivate(view)
                } else {
                    safetyView.setTextColor(ContextCompat.getColor(context!!, R.color.correct_color))
                    safetyView.setText("안전")
                    pwCheckView.setText("")
                    nextBtnActivate(view)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun pwConfirmRegExp(view:View, pwText:EditText, pwConfirmText:EditText,pwMismatchView:TextView) {
        pwConfirmText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (pwText.getText().toString() != pwConfirmText.getText().toString()) {
                    pwMismatchView.setText("불일치")
                    nextBtnActivate(view)
                } else {
                    pwMismatchView.setTextColor(ContextCompat.getColor(context!!, R.color.correct_color))
                    pwMismatchView.setText("일치")
                    nextBtnActivate(view)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun nextBtnActivate(view: View) {
        var safetyView = view.findViewById<TextView>(R.id.pw_safety_textview_signup)
        var pwMismatchView = view.findViewById<TextView>(R.id.pw_confirm_mismatch_textview_signup)
        var nextBtn = view.findViewById<Button>(R.id.next_btn_id_pw_input)

        if (safetyView.getText().toString() == "안전" && pwMismatchView.getText().toString() == "일치"){
            nextBtn.setBackgroundResource(R.drawable.login_btn)
            nextBtn.setOnClickListener{
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, EmailCertifyFragment()).commit()
            }
        }
    }

    fun idDuplicateCheck(idDuplicateBtn: Button) {
        idDuplicateBtn.setEnabled(true)
        Log.d("ms","sdfsdfsd")
    }
}