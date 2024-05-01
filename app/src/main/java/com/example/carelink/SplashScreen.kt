package com.example.carelink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.carelink.databinding.ActivitySplashScreenBinding
import java.util.zip.Inflater

class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var a: Animation
    lateinit var b: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var image1 = binding.imageView10

        a = AnimationUtils.loadAnimation(applicationContext,R.anim.sideslide)
        binding.imageView11.startAnimation(a)

        b = AnimationUtils.loadAnimation(applicationContext,R.anim.reversesideslide)
        binding.imageView12 .startAnimation(b)


        image1.alpha = 0f
        image1.animate().setDuration(3500).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, R.anim.rotate)
            finish()
        }


    }
}