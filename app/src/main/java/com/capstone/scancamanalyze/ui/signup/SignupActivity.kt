package com.capstone.scancamanalyze.ui.signup

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
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.data.request.RegisterRequest
import com.capstone.scancamanalyze.databinding.ActivitySignupBinding
import com.capstone.scancamanalyze.edittext.EmailEditText
import com.capstone.scancamanalyze.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignUpViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailEditText = findViewById<EmailEditText>(R.id.emailEditText)
        val emailEditTextLayout = findViewById<TextInputLayout>(R.id.emailEditTextLayout)
        emailEditText.setParentLayout(emailEditTextLayout)

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
        val titleAnimation =
            ObjectAnimator.ofFloat(binding.registerTitleTextView, "alpha", 1f).apply {
                duration = 500
            }

        val nameFieldAnimation =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, "alpha", 1f).apply {
                duration = 500
            }

        val emailFieldAnimation =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, "alpha", 1f).apply {
                duration = 500
            }

        val passwordFieldAnimation =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, "alpha", 1f).apply {
                duration = 500
            }

        val registerButtonAnimation =
            ObjectAnimator.ofFloat(binding.registerButton, "alpha", 1f).apply {
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
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                val request = RegisterRequest(name, email, password)
                viewModel.register(request, {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Optional: Finish SignUpActivity after successful registration
                        }
                        create()
                        show()
                    }
                }, { errorMessage ->
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Oops!") // Changed title to "Oops!"
                        setMessage(errorMessage)
                        setPositiveButton("OK", null)
                        create()
                        show()
                    }
                })
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage("Semua field harus diisi.")
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
