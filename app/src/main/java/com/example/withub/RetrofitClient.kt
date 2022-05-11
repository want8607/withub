package com.example.withub.mainFragments

import android.text.Editable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.example.withub.BuildConfig
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object RetrofitClient {   //object 는 객체를 리턴해줄 수 있어 클래스보다 편리함
    fun initRetrofit(): Retrofit {
        val url = BuildConfig.URL  //서버주소
        val gson = Gson()                      //서버와 주고받을 데이터 형식
        val clientBuilder = OkHttpClient.Builder().build()  // http통신 규약을 사용하겠다
        val connection = Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return connection
    }

    fun githubRetrofit(): Retrofit {
        val url = "https://api.github.com"
        val gson = Gson()                      //서버와 주고받을 데이터 형식
        val clientBuilder = OkHttpClient.Builder().build()  // http통신 규약을 사용하겠다
        val connection = Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return connection
    }

}

//class GithubClient {
//    val url = "https://api.github.com"
//    fun getApi() : GithubNickNameCheckApi = Retrofit.Builder()
//        .baseUrl(url)
//        .client(OkHttpClient())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(GithubNickNameCheckApi::class.java)
//
//}
//
//
////깃허브 닉네임 중복체크
//data class GithubNickNameData(
//    @SerializedName("login") val login : String
//)
//
////깃허브 아이디 유효성 체크 API
//interface GithubNickNameCheckApi{
//    @GET("users/{username}")
//    fun githubNickNameCheck(@Path("username") username: String) : Single<ArrayList<GithubNickNameData>>
//}





//아이디 중복체크
data class IdValue(
    @SerializedName("id") val id : String
)

data class IdCheckData(val message: String, val success: Boolean)

//아이디 중복체크 API
interface IdCheckApi{
    @POST("/account/duplicate/id")
    fun idCheck(@Body requestData: IdValue) : Call<IdCheckData>
}



//메일 보내기
data class EmailValue(
    @SerializedName("id") val id : String,
    @SerializedName("email") val email : String
)

data class EmailCheckData(val message: String, val success: Boolean, val token: String)

//메일 보내는 API
interface SendEmailApi{
    @POST("/account/mail")
    fun emailCheck(@Body requestData: EmailValue) : Call<EmailCheckData>
}



//메일로 보낸 인증번호 확인
data class tokenAuthEmailValue(
    @SerializedName("token") val token : String,
    @SerializedName("auth") val auth : String,
    @SerializedName("id") val id : String
)

data class CertiNumCheckData(val message: String, val success: Boolean)

//메일 보내는 API
interface CertiNumConfirmApi{
    @POST("/account/mail/auth")
    fun certiNumCheck(@Body requestData: tokenAuthEmailValue) : Call<CertiNumCheckData>
}



//윗허브 닉네임 중복체크
data class NickNameValue(
    @SerializedName("nickname") val nickname : String
)

data class NickNameCheckData(val message: String, val success: Boolean)

//아이디 중복체크 API
interface NickNameCheckApi{
    @POST("/account/duplicate/nickname")
    fun nickNameCheck(@Body requestData: NickNameValue) : Call<NickNameCheckData>
}







//깃허브 닉네임,오너,레포지토리 체크
data class GithubOwnerRepoValue(
    @SerializedName("committer") val committer : String,
    @SerializedName("owner") val owner : String,
    @SerializedName("name") val name : String
)

data class GitHubOwnerRepoCheckData(val message: String, val success: Boolean)

//깃허브 아이디 유효성 체크 API
interface GithubOwnerRepoCheckApi{
    @POST("/account/github")
    fun githubOwnerRepoCheck(@Body requestData: GithubOwnerRepoValue) : Call<GitHubOwnerRepoCheckData>
}


//아이디 찾기 메일전송
data class FindIdEmailValue(
    @SerializedName("email") val email : String
)

data class IdFindEmailCheckData(val message: String, val success: Boolean, val token: String)

//아이디찾기(메일전송) API
interface FindIdSendEmailApi{
    @POST("/account/id")
    fun emailCheck(@Body requestData: FindIdEmailValue) : Call<IdFindEmailCheckData>
}



//회원가입 하는
data class AccountValue(
    @SerializedName("id") val id : String,
    @SerializedName("pw") val pw : String,
    @SerializedName("email") val email : String,
    @SerializedName("area") val area : String,
    @SerializedName("committer") val committer : String,
    @SerializedName("repository") val repository : List<AccountRepositoryValue>

    )

data class AccountRepositoryValue(
    @SerializedName("owner") val owner : String,
    @SerializedName("name") val name : String
)

data class AccountCheckData(val message: String, val success: Boolean)


interface MakeAccountApi{
    @POST("/account")
    fun accountCheck(@Body requestData: AccountValue) : Call<AccountCheckData>
}
