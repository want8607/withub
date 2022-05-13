package com.example.withub

import android.content.ContentValues.TAG
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object GithubClient {


//    fun testfun():Retrofit {
//        // okhttp 인스턴스 생성
//        val client = OkHttpClient.Builder()
//
//        // 로그를 찍기 위해 로깅 인터셉터 설정
//        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
//
//            override fun log(message: String) {
//                Log.d(TAG, "RetrofitClient - log() called / message: $message")
//
//                when {
//                    message.isJsonObject() ->
//                        Log.d(TAG, JSONObject(message).toString(4))
//                    message.isJsonArray() ->
//                        Log.d(TAG, JSONObject(message).toString(4))
//                    else -> {
//                        try {
//                            Log.d(TAG, JSONObject(message).toString(4))
//                        } catch (e: Exception) {
//                            Log.d(TAG, message)
//                        }
//                    }
//                }
//
//            }
//
//        })
//
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
//        client.addInterceptor(loggingInterceptor)
//
//
//        // 기본 파라매터 인터셉터 설정
//        val baseParameterInterceptor : Interceptor = (object : Interceptor {
//
//                override fun intercept(chain: Interceptor.Chain): Response {
//                Log.d(TAG, "RetrofitClient - intercept() called")
//                // 오리지날 리퀘스트
//                val originalRequest = chain.request()
//
//                // 쿼리 파라매터 추가하기
//                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
//
//                val finalRequest = originalRequest.newBuilder()
//                    .url(addedUrl)
//                    .method(originalRequest.method, originalRequest.body)
//                    .build()
//
//                val response = chain.proceed(finalRequest)
//
//                if (response.code != 200) {
//
//                    android.os.Handler(Looper.getMainLooper()).post {
//                        Toast.makeText(App.instance, "${response.code} 에러 입니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                return response
//            }
//
//        })
//
//
//        // 위에서 설정한 기본파라매터 인터셉터를 okhttp 클라이언트에 추가한다.
//        client.addInterceptor(baseParameterInterceptor)
//
//        // 커넥션 타임아웃
//        client.connectTimeout(10, TimeUnit.SECONDS)
//        client.readTimeout(10, TimeUnit.SECONDS)
//        client.writeTimeout(10, TimeUnit.SECONDS)
//        client.retryOnConnectionFailure(true)
//
//
//        if(retrofitClient == null){
//
//            // 레트로핏 빌더를 통해 인스턴스 생성
//            retrofitClient = Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//
//                // 위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
//                .client(client.build())
//
//                .build()
//        }
//
//        return retrofitClient

//    }
//
//
//    fun String?.isJsonObject():Boolean {
//        return this?.startsWith("{") == true && this.endsWith("}")
//    }
//
//    // 문자열이 제이슨 배열인지
//    fun String?.isJsonArray() : Boolean {
//        return this?.startsWith("[") == true && this.endsWith("]")
//    }



    fun getApi() : Retrofit {
        val url = "http://api.github.com/"
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
interface GithubNickNameCheckApi{
    @GET("users/{username}")
    fun githubNickNameCheck(@Path("username") username: String) : retrofit2.Call<GithubNickNameData>
}



//깃허브 닉네임,오너,레포지토리 체크
data class LoginListData(
    @SerializedName("login") val login : String
)

//깃허브 레포 유효성 체크 API
interface GithubOwnerRepoCheckApi{
    @GET("repos/{owner}/{repo}/contributors")
    fun githubOwnerRepoCheck(@Path("owner") owner: String, @Path("repo") repo: String)
    : retrofit2.Call<List<LoginListData>>
}