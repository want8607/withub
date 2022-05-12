package com.example.withub

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object GithubClient {

    fun getApi() : Retrofit {
        val url = "https://api.github.com"
        val gson = Gson()
        val clientBuilder = OkHttpClient.Builder().build()  // http통신 규약을 사용하겠다
        val connection = Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            return connection
    }
}

//깃허브 닉네임 중복체크
data class GithubNickNameData(
    @SerializedName("login") val login : String
)



//깃허브 아이디 유효성 체크 API
//interface GithubNickNameCheckApi{
//    @GET("users/{username}")
//    fun githubNickNameCheck(@Path("username") username: String) : Single<ArrayList<GithubNickNameData>>
//}
