package com.crosoften.emnuvem.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.crosoften.emnuvem.databinding.ActivitySplashScreenBinding
import com.crosoften.emnuvem.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAnimation()
    }

    private fun setupAnimation() {
        lifecycleScope.launch {
            var count = 0f
            for (i in 1..50) {
                binding.logo.alpha = count
                count += 0.02f
                delay(25)
            }
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        }
    }
}