package com.capstone.scancamanalyze.ui.welcome

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.databinding.ActivityWelcomeBinding
import com.capstone.scancamanalyze.ui.login.LoginActivity
import com.capstone.scancamanalyze.ui.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(100)
        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        // Tombol Login
        binding.loginButton.setOnClickListener {
            startButtonAnimation(binding.loginButton) { // Panggil animasi pada tombol login
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                ) // Setelah animasi selesai, buka LoginActivity
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                ) // Efek transisi antar halaman
            }
        }

        // Tombol Signup
        binding.signupButton.setOnClickListener {
            startButtonAnimation(binding.signupButton) { // Panggil animasi pada tombol signup
                startActivity(
                    Intent(
                        this,
                        SignupActivity::class.java
                    )
                )
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }
        }
    }
    private fun startButtonAnimation(view: View, onAnimationEnd: () -> Unit) {
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.1f, 1f).setDuration(300)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.1f, 1f).setDuration(300)
        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0.7f, 1f).setDuration(300)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    onAnimationEnd()
                }
            })
            start()
        }
    }
}