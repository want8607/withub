package com.example.withub

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.example.withub.mainFragments.LoginActivity


//class SplashActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.SplashTheme)
//        super.onCreate(savedInstanceState)
//
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//        finish()
//
//    }
//}


class SplashActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Start home activity
//        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//        // close splash activity
//        finish()
//    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...

        // Add a callback that's called when the splash screen is animating to
        // the app content.
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenView.remove() }

            // Run your animation.
            slideUp.start()
        }
    }

}