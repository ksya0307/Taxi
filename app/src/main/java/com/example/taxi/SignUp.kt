package com.example.taxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taxi.Requests.SignupRequest
import com.example.taxi.Responses.SignupResponse
import com.example.taxi.queries.ApiClient
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        apiClient = ApiClient()

        signUp.setOnClickListener {

            if(passwordInput.text.toString()!= passwordRepeatInput.text.toString()){
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            }else{
                if(passwordInput.text.toString().isNotEmpty() &&
                    passwordRepeatInput.text.toString().isNotEmpty() &&
                    usernameInput.text.toString().isNotEmpty() &&
                    emailInput.text.toString().isNotEmpty()){

                    apiClient.getApiService().signup(SignupRequest(username =  usernameInput.text.toString(), email = emailInput.text.toString(), password = passwordInput.text.toString()))
                        .enqueue(object : Callback<SignupResponse>{
                            override fun onResponse(
                                call: Call<SignupResponse>,
                                response: Response<SignupResponse>
                            ) {
                                if(response.body() != null){
                                    val signup = response.body()

                                    if (signup != null) {
                                        if(signup.notice["answer"] == "Success"){
                                            Toast.makeText(applicationContext, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(applicationContext, MainActivity::class.java))
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                              t.printStackTrace()
                            }

                        })

                }else{
                    Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}