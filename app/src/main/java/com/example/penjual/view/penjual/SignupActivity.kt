package com.example.penjual.view.penjual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataPenjualResponse
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_signup.setOnClickListener {
            val usernamePenjual = edt_username_penjual.text.toString()
            val passwordPenjual = edt_password_penjual.text.toString()
            val namaPenjual = edt_nama_penjual.text.toString()
            val telpPenjual = edt_telp_penjual.text.toString()
            val alamatPenjual = edt_alamat_penjual.text.toString()
            when {
                usernamePenjual.isEmpty() -> {
                    edt_username_penjual.error = "Masih kosong"
                }
                passwordPenjual.isEmpty() -> {
                    edt_password_penjual.error = "Masih kosong"
                }
                namaPenjual.isEmpty() -> {
                    edt_nama_penjual.error = "Masih kosong"
                }
                telpPenjual.isEmpty() -> {
                    edt_telp_penjual.error = "Masih kosong"
                }
                alamatPenjual.isEmpty() -> {
                    edt_alamat_penjual.error = "Masih kosong"
                }
                else -> {
                    RetrofitClient.instance.postPenjual(usernamePenjual, passwordPenjual, namaPenjual,
                        telpPenjual, alamatPenjual).enqueue(object :
                        Callback<DataPenjualResponse.PenjualResponse> {
                        override fun onFailure(call: Call<DataPenjualResponse.PenjualResponse>, t: Throwable) {
                            Toast.makeText(this@SignupActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataPenjualResponse.PenjualResponse>,
                            response: Response<DataPenjualResponse.PenjualResponse>
                        ) {
                            edt_username_penjual.text.clear()
                            edt_password_penjual.text.clear()
                            edt_nama_penjual.text.clear()
                            edt_telp_penjual.text.clear()
                            edt_alamat_penjual.text.clear()

                            Toast.makeText(this@SignupActivity, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}