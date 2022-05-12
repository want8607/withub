package com.example.withub.loginFragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.withub.*
import com.example.withub.mainFragments.mainFragmentAdapters.SignupRVAdapter
import retrofit2.Call
import retrofit2.Response

class GitHubRepositoryAddFragment: Fragment() {
    lateinit var githubNickNameValue:String
    var repositoryList = ArrayList<UserRepoData>()
    lateinit var adapter : SignupRVAdapter
    var githubNickNameCheckBoolean = false
    val retrofit = RetrofitClient.initRetrofit()
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.github_repositoryadd_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val githubNickNameText = view.findViewById<EditText>(R.id.github_nickname_edittext_signup)
        val githubNickNameConfirmBtn = view.findViewById<Button>(R.id.github_nickname_confirm_btn_signup)
        val githubNickNameChangeBtn = view.findViewById<Button>(R.id.github_nickname_change_btn_signup)
        val repositoryAddBtn = view.findViewById<Button>(R.id.repository_add_btn_signup)
        val githubOwnerText = view.findViewById<EditText>(R.id.owner_edittext_signup)
        val githubRepositoryText = view.findViewById<EditText>(R.id.repository_edittext_signup)
        val signupActivity = activity as SignupActivity
        val signupBackBtn = signupActivity.findViewById<Button>(R.id.signup_back_btn)
        val signupText = signupActivity.findViewById<TextView>(R.id.signup_text)
        val warningInform1 = signupActivity.findViewById<TextView>(R.id.warning_inform_signup_1)
        val warningInform2 = signupActivity.findViewById<TextView>(R.id.warning_inform_signup_2)
        val warningInform3 = signupActivity.findViewById<TextView>(R.id.warning_inform_signup_3)
        val warningInform4 = signupActivity.findViewById<TextView>(R.id.warning_inform_signup_4)
        signupText.visibility = GONE
        warningInform1.visibility = VISIBLE
        warningInform2.visibility = VISIBLE
        warningInform3.visibility = VISIBLE
        warningInform4.visibility = VISIBLE

        textViewColorChange(warningInform1,16,24,R.color.timer)
        textViewColorChange(warningInform2,1,6,R.color.timer)
        textViewColorChange(warningInform4,0,34,R.color.timer)

//        adapter = SignupRVAdapter(repositoryList)
        recyclerView.adapter = adapter

        signupBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, NickNameAreaSelectFragment())
                .commit()
        }

        githubNickNameConfirmBtn.setOnClickListener{
            githubNickNameDuplicateApi(view,githubNickNameText,githubNickNameConfirmBtn,githubNickNameChangeBtn)
        }

        repositoryAddBtn.setOnClickListener{
            if (githubNickNameCheckBoolean == false) {
                dialogMessage("GitHub 닉네임을 먼저 확인해주세요.")
            } else {
                githubOwnerRepoCheckApi(githubOwnerText,githubRepositoryText,repositoryList,adapter)
            }
        }

        githubNickNameChangeBtn.setOnClickListener{
            githubNickNameCheckBoolean = false
            githubNickNameConfirmBtn.visibility = VISIBLE
            githubNickNameChangeBtn.visibility = GONE
            githubNickNameText.isEnabled = true
            nextBtnActivate(view)
        }

        githubNickNameRegEx(githubNickNameText,githubNickNameConfirmBtn)
        githubOwnerRepoRegEx(githubOwnerText,githubRepositoryText,repositoryAddBtn)

        return view
    }

    fun textViewColorChange(textView: TextView, start:Int, end:Int, color: Int) {
        val builder = SpannableStringBuilder(textView.text.toString())
        val colorPoint = ForegroundColorSpan(resources.getColor(color,null))
        builder.setSpan(colorPoint,start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = builder
    }

    fun githubNickNameRegEx(githubNickNameText:EditText,githubNickNameConfirmBtn:Button) {
        githubNickNameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (githubNickNameText.text.toString().isNotEmpty()) {
                    githubNickNameConfirmBtn.setBackgroundResource(R.drawable.stroke_btn)
                    githubNickNameConfirmBtn.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    githubNickNameConfirmBtn.isEnabled = true
                } else {
                    githubNickNameConfirmBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
                    githubNickNameConfirmBtn.setTextColor(ContextCompat.getColor(context!!, R.color.thick_gray))
                    githubNickNameConfirmBtn.isEnabled = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun githubOwnerRepoRegEx(githubOwnerText: EditText,githubRepositoryText: EditText,repositoryAddBtn:Button){
        githubOwnerText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (githubOwnerText.text.toString().isNotEmpty() && githubRepositoryText.text.toString().isNotEmpty()) {
                    activateAddBtn(repositoryAddBtn)
                } else {
                    disabledAddBtn(repositoryAddBtn)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        githubRepositoryText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (githubOwnerText.text.toString().isNotEmpty() && githubRepositoryText.text.toString().isNotEmpty()) {
                    activateAddBtn(repositoryAddBtn)
                } else {
                    disabledAddBtn(repositoryAddBtn)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun activateAddBtn(repositoryAddBtn: Button){
        repositoryAddBtn.setBackgroundResource(R.drawable.stroke_btn)
        repositoryAddBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        repositoryAddBtn.isEnabled = true
    }

    fun disabledAddBtn(repositoryAddBtn: Button){
        repositoryAddBtn.setBackgroundResource(R.drawable.stroke_disabled_btn)
        repositoryAddBtn.setTextColor(ContextCompat.getColor(requireContext(),R.color.thick_gray))
        repositoryAddBtn.isEnabled = false
    }

    fun githubNickNameDuplicateApi(view:View, githubNickNameText: EditText,githubNickNameConfirmBtn: Button,githubNickNameChangeBtn:Button){
        val inform = GithubNickNameValue(githubNickNameText.text.toString())
        val requestGithubNickNameCheckApi = retrofit.create(GithubNickNameCheckApi::class.java)
        requestGithubNickNameCheckApi.githubNickNameCheck(inform).enqueue(object : retrofit2.Callback<GitHubNickNameCheckData> {
            override fun onFailure(
                call: Call<GitHubNickNameCheckData>,
                t: Throwable
            ) {
            }
            override fun onResponse(call: Call<GitHubNickNameCheckData>, response: Response<GitHubNickNameCheckData>) {

                if (!response.body()!!.success) {
                    dialogMessage("일치하는 GitHub 닉네임이 없습니다.")
                } else {
                    Log.d("message","${response.body()!!.success}")
                    githubNickNameValue =githubNickNameText.text.toString()
                    githubNickNameCheckBoolean = true
                    githubNickNameConfirmBtn.visibility = GONE
                    githubNickNameChangeBtn.visibility = VISIBLE
                    githubNickNameText.isEnabled = false
                    nextBtnActivate(view)
                    dialogMessage("GiHub 닉네임이 확인되었습니다.")
                }
            }
        })
    }

    fun githubOwnerRepoCheckApi(githubOwnerText: EditText, githubRepositoryText:EditText, repositoryList: ArrayList<UserRepoData>, adapter:RecyclerView.Adapter<SignupRVAdapter.Holder>) {
        val inform = GithubOwnerRepoValue(githubNickNameValue,githubOwnerText.text.toString(),githubRepositoryText.text.toString())
        val requestGithubOwnerRepoCheckApi = retrofit.create(GithubOwnerRepoCheckApi::class.java)
        requestGithubOwnerRepoCheckApi.githubOwnerRepoCheck(inform).enqueue(object : retrofit2.Callback<GitHubOwnerRepoCheckData> {
            override fun onFailure(
                call: Call<GitHubOwnerRepoCheckData>,
                t: Throwable
            ) {
            }
            override fun onResponse(call: Call<GitHubOwnerRepoCheckData>, response: Response<GitHubOwnerRepoCheckData>) {
                if (!response.body()!!.success) {
                    dialogMessage("일치하는 GitHub 닉네임이 없습니다.")
                } else {
                    addRepository(repositoryList,"${githubOwnerText.text} / ${githubRepositoryText.text}")
                    dialogMessage("레포지토리가 추가되었습니다.")
                }
            }
        })
    }

    fun addRepository(repositoryList:ArrayList<UserRepoData>, ownerRepository:String) {
//        repositoryList.add(UserRepoData("$ownerRepository"))
//        adapter.addItem(UserRepoData(ownerRepository))
    }


    fun dialogMessage(message:String) {
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton("확인", null)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun nextBtnActivate(view: View) {

    }
}