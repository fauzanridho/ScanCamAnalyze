package com.capstone.scancamanalyze.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        startAnimation()
        setupAction()
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

    private fun startAnimation() {
        val titleAnimation = ObjectAnimator.ofFloat(binding.registerTitleTextView, "alpha", 1f).apply {
            duration = 500
        }

        val nameFieldAnimation = ObjectAnimator.ofFloat(binding.nameEditTextLayout, "alpha", 1f).apply {
            duration = 500
        }

        val emailFieldAnimation = ObjectAnimator.ofFloat(binding.emailEditTextLayout, "alpha", 1f).apply {
            duration = 500
        }

        val passwordFieldAnimation = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, "alpha", 1f).apply {
            duration = 500
        }

        val registerButtonAnimation = ObjectAnimator.ofFloat(binding.registerButton, "alpha", 1f).apply {
            duration = 500
        }

        AnimatorSet().apply {
            interpolator = DecelerateInterpolator()
            playSequentially(
                titleAnimation,
                nameFieldAnimation,
                emailFieldAnimation,
                passwordFieldAnimation,
                registerButtonAnimation
            )
            start()
        }
    }


    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }
}