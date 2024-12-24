package com.emnuvem.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.emnuvem.databinding.ActivitySplashScreenBinding
import com.emnuvem.ui.activity.auth.LoginActivity
import com.emnuvem.ui.activity.main.MainActivity
import com.emnuvem.ultils.Preference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var preferences: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        preferences = Preference(binding.root.context)
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

            if (preferences.getToken().isNullOrEmpty()) {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}