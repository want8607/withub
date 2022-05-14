package com.example.withub

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.gson.GsonBuilder


class ForegroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

//        val requestCommitApi= GithubClient.getApi().create(InfoApi::class.java)
//
//        GlobalScope.launch{
//            repeat (true){
//                val gitHubCommitDatas: GitHubCommitDatas = withContext(Dispatchers.IO) {
//                    requestCommitApi.getInfo("want8607", "stageus_withub_2")
//                }
//                val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
//                var result = gsonBuilder.toJson(gitHubCommitDatas)
//                Log.d("결과:",result)
//            }
//            delay()
//        }


        return START_STICKY
    }

}