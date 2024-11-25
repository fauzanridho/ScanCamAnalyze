package com.capstone.scancamanalyze.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.scancamanalyze.MainActivity
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.databinding.ActivityLoginBinding
import com.capstone.scancamanalyze.data.pref.UserModel

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        startAnimation()
        setupAction()
    }
    private fun startAnimation() {
        val titleAnimation = ObjectAnimator.ofFloat(binding.titleTextView, "alpha", 1f).apply {
            duration = 500
        }

        val messageAnimation = ObjectAnimator.ofFloat(binding.messageTextView, "alpha", 1f).apply {
            duration = 500
        }

        val emailTextAnimation = ObjectAnimator.ofFloat(binding.emailTextView, "alpha", 1f).apply {
            duration = 500
        }

        val emailFieldAnimation =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, "alpha", 1f).apply {
                duration = 500
            }

        val passwordTextAnimation =
            ObjectAnimator.ofFloat(binding.passwordTextView, "alpha", 1f).apply {
                duration = 500
            }

        val passwordFieldAnimation =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, "alpha", 1f).apply {
                duration = 500
            }

        val buttonAnimation = ObjectAnimator.ofFloat(binding.loginButton, "alpha", 1f).apply {
            duration = 500
        }
        AnimatorSet().apply {
            interpolator = DecelerateInterpolator()
            playSequentially(
                titleAnimation,
                messageAnimation,
                emailTextAnimation,
                emailFieldAnimation,
                passwordTextAnimation,
                passwordFieldAnimation,
                buttonAnimation
            )
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
        binding.loginButton.setOnClickListener {
            showLoading(true) // Tampilkan progress bar
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Simulasi proses login
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.saveSession(UserModel(email, "sample_token"))
                showLoading(false) // Sembunyikan progress bar setelah selesai
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                showLoading(false) // Sembunyikan progress bar jika ada kesalahan
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage("Email atau password tidak boleh kosong.")
                    setPositiveButton("OK", null)
                    create()
                    show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}