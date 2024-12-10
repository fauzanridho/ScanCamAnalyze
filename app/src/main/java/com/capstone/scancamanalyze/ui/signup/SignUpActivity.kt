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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.databinding.ActivitySignupBinding
import com.capstone.scancamanalyze.edittext.EmailEditText
import com.capstone.scancamanalyze.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

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
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        showLoading(false)
                        if (task.isSuccessful) {
                            AlertDialog.Builder(this).apply {
                                setTitle("Registrasi Berhasil")
                                setMessage("Akun berhasil dibuat. Silakan login.")
                                setPositiveButton("OK") { _, _ ->
                                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        } else {
                            AlertDialog.Builder(this).apply {
                                setTitle("Registrasi Gagal")
                                setMessage(task.exception?.localizedMessage ?: "Terjadi kesalahan")
                                setPositiveButton("OK", null)
                                create()
                                show()
                            }
                        }
                    }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage("Email dan password harus diisi")
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
