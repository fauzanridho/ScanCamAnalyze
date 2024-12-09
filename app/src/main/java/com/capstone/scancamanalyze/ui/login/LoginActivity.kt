package com.capstone.scancamanalyze.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.capstone.scancamanalyze.MainActivity
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.databinding.ActivityLoginBinding
import com.capstone.scancamanalyze.edittext.EmailEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        userPreference = UserPreference.getInstance(dataStore)

        val emailEditText = findViewById<EmailEditText>(R.id.emailEditText)
        val emailEditTextLayout = findViewById<TextInputLayout>(R.id.emailEditTextLayout)
        emailEditText.setParentLayout(emailEditTextLayout)

        setupView()
        startAnimation()
        setupAction()
    }

    private fun startAnimation() {
        val titleAnimation = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).apply {
            duration = 500
        }

        val messageAnimation = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).apply {
            duration = 500
        }

        val emailTextAnimation = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).apply {
            duration = 500
        }

        val emailFieldAnimation =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).apply {
                duration = 500
            }

        val passwordTextAnimation =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).apply {
                duration = 500
            }

        val passwordFieldAnimation =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).apply {
                duration = 500
            }

        val buttonAnimation = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).apply {
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
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        showLoading(false)
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                val userModel = UserModel(user.displayName ?: "", user.email ?: "", true)
                                lifecycleScope.launch {
                                    userPreference.saveSession(userModel)
                                }
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            AlertDialog.Builder(this).apply {
                                setTitle("Login Gagal")
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