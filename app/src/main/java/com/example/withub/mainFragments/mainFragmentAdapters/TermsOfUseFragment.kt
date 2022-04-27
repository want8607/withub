package com.example.withub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.example.withub.R
import com.example.withub.mainFragments.mainFragmentAdapters.IdPwInputFragment

class TermsOfUseFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.termsofuse_fragment, container, false)
        var signupActivity = activity as SignupActivity
        var signupBackBtn = signupActivity.findViewById<Button>(R.id.signup_back_btn)

        var checkBoxAllCheck = view.findViewById<CheckBox>(R.id.checkbox_all_check)
        var checkBox1 = view.findViewById<CheckBox>(R.id.checkbox_terms_of_use_1)
        var checkBox2 = view.findViewById<CheckBox>(R.id.checkbox_terms_of_use_2)

        var nextBtn = view.findViewById<Button>(R.id.next_btn_terms_of_use)

        signupBackBtn.setOnClickListener{
            val intent = Intent(signupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener{
            if ( checkBox1.isChecked && checkBox2.isChecked) {
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, IdPwInputFragment()).commit()
            }
        }

        checkBoxAllCheck.setOnClickListener { onCheckChanged(checkBoxAllCheck,checkBoxAllCheck,checkBox1,checkBox2,nextBtn) }
        checkBox1.setOnClickListener { onCheckChanged(checkBox1,checkBoxAllCheck,checkBox1,checkBox2,nextBtn) }
        checkBox2.setOnClickListener { onCheckChanged(checkBox2,checkBoxAllCheck,checkBox1,checkBox2,nextBtn) }

        return view
    }

    private fun onCheckChanged(compoundButton: CompoundButton,checkBoxAllCheck:CheckBox,checkBox1:CheckBox,checkBox2:CheckBox,nextBtn:Button) {
        when(compoundButton.id) {
            R.id.checkbox_all_check -> {
                if (checkBoxAllCheck.isChecked) {
                    checkBox1.isChecked = true
                    checkBox2.isChecked = true
                    nextBtn.setBackgroundResource(R.drawable.login_btn)
                }else {
                    checkBox1.isChecked = false
                    checkBox2.isChecked = false
                    nextBtn.setBackgroundResource(R.drawable.disabled_button)
                }
            }
            else -> {
                checkBoxAllCheck.isChecked = (
                        checkBox1.isChecked
                                && checkBox2.isChecked)
                if (checkBox1.isChecked && checkBox2.isChecked) {
                    nextBtn.setBackgroundResource(R.drawable.login_btn)
                } else{
                    nextBtn.setBackgroundResource(R.drawable.disabled_button)
                }
            }
        }
    }

}



