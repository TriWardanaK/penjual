package com.example.penjual.view.penjual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adapter.PesananAdapter
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.penjual.view.MainActivity
import com.example.response.DataLoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_signup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val usernamePenjual = edt_username_penjual.text.toString()
            val passwordPenjual = edt_password_penjual.text.toString()
            when {
                usernamePenjual.isEmpty() -> {
                    edt_username_penjual.error = "Masih kosong"
                }
                passwordPenjual.isEmpty() -> {
                    edt_password_penjual.error = "Masih kosong"
                }
                else -> {
                    RetrofitClient.instance.postLoginPenjual(usernamePenjual, passwordPenjual).enqueue(object :
                        Callback<DataLoginResponse> {
                        override fun onFailure(call: Call<DataLoginResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataLoginResponse>,
                            response: Response<DataLoginResponse>
                        ) {
                            if (response.body()?.idPenjual != null) {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra(MainActivity.EXTRA_PENJUAL_ID, response.body())
                                Log.d("cek id penjual", "${response.body()?.idPenjual}")
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@LoginActivity, "Username/Password Salah", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }
}