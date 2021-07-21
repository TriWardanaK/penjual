package com.example.penjual.view.penjual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataLoginResponse
import com.example.response.DataPenjualResponse
import com.example.response.DetailPenjualResponse
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PENJUAL_ID = "extra_penjual_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        showPenjual()

        btn_update.setOnClickListener {
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
                    RetrofitClient.instance.putPenjual(getId().toString(), usernamePenjual, passwordPenjual, namaPenjual,
                        telpPenjual, alamatPenjual).enqueue(object :
                        Callback<DataPenjualResponse.PenjualResponse> {
                        override fun onFailure(call: Call<DataPenjualResponse.PenjualResponse>, t: Throwable) {
                            Toast.makeText(this@UpdateActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataPenjualResponse.PenjualResponse>,
                            response: Response<DataPenjualResponse.PenjualResponse>
                        ) {
                            Toast.makeText(this@UpdateActivity, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    private fun getId(): Int? {
        val penjualId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?
        return penjualId?.idPenjual
    }

    private fun showPenjual() {
        RetrofitClient.instance.getDetailPenjual(getId()).enqueue(object :
            Callback<DetailPenjualResponse> {
            override fun onFailure(call: Call<DetailPenjualResponse>, t: Throwable) {
                Toast.makeText(this@UpdateActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailPenjualResponse>,
                response: Response<DetailPenjualResponse>
            ) {
                val detail = response.body()
                edt_username_penjual.setText(detail?.usernamePenjual)
                edt_password_penjual.setText(detail?.passwordPenjual)
                edt_nama_penjual.setText(detail?.namaPenjual)
                edt_telp_penjual.setText(detail?.telpPenjual)
                edt_alamat_penjual.setText(detail?.alamatPenjual)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_account -> {
                RetrofitClient.instance.deletePenjual(getId()).enqueue(object :
                    Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@UpdateActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        Toast.makeText(this@UpdateActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                        finishAffinity()
                        val intent = Intent(this@UpdateActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                })
                true
            }
            else -> true
        }
    }
}