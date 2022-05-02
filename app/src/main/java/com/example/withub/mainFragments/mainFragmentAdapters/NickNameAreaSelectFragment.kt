package com.example.withub.mainFragments.mainFragmentAdapters

import android.os.Bundle
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
import java.util.regex.Pattern

class NickNameAreaSelectFragment: Fragment() {
    lateinit var areaSelect: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.nickname_areaselect_fragment, container, false)
        var signupActivity = activity as SignupActivity
        var signupBackBtn = signupActivity.findViewById<Button>(R.id.signup_back_btn)
        var spinner: Spinner = view.findViewById(R.id.area_spinner_signup)
        var nickNameText = view.findViewById<EditText>(R.id.nickname_edittext_signup)
        var nickNameDuplicateBtn = view.findViewById<Button>(R.id.nickname_duplicate_check_btn_signup)
        var nickNameCheckView = view.findViewById<TextView>(R.id.nickname_check_textview_signup)
        var nextBtn = view.findViewById<Button>(R.id.next_btn_nickname_area_select)

        signupBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, EmailCertifyFragment())
                .commit()
        }

        nextBtn.setOnClickListener {
//            if (areaSelect == "--선택--") {
//                Log.d("message", "지역을 선택하세요")
//            } else {
//            }
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, GitHubRepositoryAddFragment())
                .commit()
        }

        nickNameDuplicateBtn.setOnClickListener{
                //에이피아이 확인
                //닉네임, 엑티비티로 보내가ㅣ
        }

        nickNameRegExp(nickNameText,nickNameDuplicateBtn,nickNameCheckView)
        areaSpinnerSelect(spinner)
        return view
    }

    fun areaSpinnerSelect(spinner:Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
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

    fun nickNameRegExp(nickNameText:EditText,nickNameDuplicateBtn:Button,nickNameCheckView:TextView){
        nickNameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var boolean = Pattern.matches("^[a-z0-9가-힣]*$", nickNameText.getText().toString())
                if (boolean==false || nickNameText.getText().length < 2) {
                    nickNameCheckView.setText("2~10자 한글, 영어, 숫자만 사용하세요.")
                    nickNameDuplicateBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    nickNameDuplicateBtn.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
                    nickNameDuplicateBtn.setEnabled(false)
                } else {
                    nickNameCheckView.setText("")
                    nickNameDuplicateBtn.setBackgroundResource(R.drawable.stroke_btn)
                    nickNameDuplicateBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    nickNameDuplicateBtn.setEnabled(true)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}