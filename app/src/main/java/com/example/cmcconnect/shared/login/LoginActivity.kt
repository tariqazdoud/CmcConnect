package com.example.cmcconnect.shared.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cmcconnect.MainActivity
import com.example.cmcconnect.R
import com.example.cmcconnect.model.UserInInfo
import com.example.cmcconnect.shared.User.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button

    private val signInViewModel: SignInViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt = findViewById(R.id.emailEditText)
        passwordEt = findViewById(R.id.passwordEditText)
        loginBtn = findViewById(R.id.login_button)

        loginBtn.setOnClickListener {
            val userEmail = emailEt.text.toString()
            val userPassword = passwordEt.text.toString()

            signInViewModel.onSignIn(userEmail, userPassword)
        }
        viewModel.test.observe(this@LoginActivity, Observer { test ->
            Log.d("testData", "$test")
        })
        viewModel.getYear()
        observeSignInState()
    }

    private fun observeSignInState() {
        lifecycleScope.launchWhenStarted {
            signInViewModel.signInState.collect { state ->
                when (state) {
                    is SignInViewModel.SignInState.Success -> {
                        if (state.success) {
                            Log.d("LoginActivity", "Sign-In success")
                            userViewModel.userInfoLiveDate.observe(this@LoginActivity) { userInfo ->
                                Log.d("userInInfo", "$userInfo")
                                if (userInfo != null) {
                                    UserInInfo.id = userInfo.id!!
                                    UserInInfo.name = userInfo.name.toString()
                                    UserInInfo.email = userInfo.email.toString()
                                    UserInInfo.phone = userInfo.phone.toString()
                                    UserInInfo.image = userInfo.image.toString()
                                    UserInInfo.id_groupe_fk = userInfo.id_groupe_fk
                                    UserInInfo.id_type_user_fk = userInfo.id_type_user_fk!!
                                    UserInInfo.id_pole_fk = userInfo.id_pole_fk
                                }
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            userViewModel.getUserInInfo()

                        } else {

                        }

                    }

                    is SignInViewModel.SignInState.Error -> {}
                    is SignInViewModel.SignInState.Initial -> {}
                    is SignInViewModel.SignInState.Loading -> {}
                }
            }
        }
    }

}