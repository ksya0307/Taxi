package com.example.taxi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taxi.Requests.LoginRequest
import com.example.taxi.Responses.LoginResponse
import com.example.taxi.queries.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var apiClient:ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiClient = ApiClient()


        createAccount.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        signIn.setOnClickListener {

            if(usernameInput.text.toString().isEmpty() || passwordInput.text.toString().isEmpty()){
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }else{
                apiClient.getApiService().login(LoginRequest(username = usernameInput.text.toString(), password = passwordInput.text.toString()))
                    .enqueue(object : Callback<LoginResponse>{
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            val logIn = response.body()
                            if (logIn != null) {
                                if(logIn.notice["token"]!=null){
                                    val doubleToken:Double = logIn.notice["token"] as Double
                                    val token:Int = doubleToken.toInt()

                                        println("${logIn.notice["token"]} вы авторизованы $token")
                                        val intent = Intent(applicationContext, MapsActivity::class.java)
                                        intent.putExtra("token", token)
                                        startActivity(intent)

                                }else{
                                    println("${logIn.notice["answer"]}")
                                }
                            }
                                //println("Token: ${logIn.notice["token"]}, Status: ${logIn.notice["status"]}")
                            }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                           t.printStackTrace()
                        }

                    }
                )
            }
        }
    }
}