package com.rogergcc.filmsthemoviedbapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.rogergcc.filmsthemoviedbapp.MainActivity
import com.rogergcc.filmsthemoviedbapp.R
import com.rogergcc.filmsthemoviedbapp.databinding.ActivitySplashscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Splashscreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    private var topAnimation: Animation? = null
    private var bottomAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {

//        super.onCreate(savedInstanceState);
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splashscreen)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

//        binding.imvLogo.animation = animationScale

//        Handler().postDelayed({
//            val i = Intent(this@Splashscreen, MainActivity::class.java)
//            startActivity(i)
//            finish()
//        }, Companion.SPLASH_TIME_OUT.toLong())

        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_TIME_OUT.toLong())

            Intent(this@Splashscreen, MainActivity::class.java).apply {
                startActivity(this)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            finish()

        }
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }
}