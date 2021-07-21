package com.example.penjual.view.barang

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.penjual.view.WebViewActivity
import com.example.response.DataBarangResponse
import kotlinx.android.synthetic.main.activity_add_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBarangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_barang)

        btn_save.setOnClickListener {
            val namaBarang = edt_nama_barang.text.toString()
            val hargaBarang = edt_harga_barang.text.toString()
            val negaraAsal = edt_negara_asal.text.toString()
            val merkBarang = edt_merk_barang.text.toString()
            val masaGaransi = edt_masa_garansi.text.toString()
            val beratBarang = edt_berat_barang.text.toString()
            val stokBarang = edt_stok_barang.text.toString()
            val fotoBarang = edt_foto_barang.text.toString()
            val deskripsiBarang = edt_deskripsi_barang.text.toString()
            when {
                namaBarang.isEmpty() -> {
                    edt_nama_barang.error = "Masih kosong"
                }
                hargaBarang.isEmpty() -> {
                    edt_harga_barang.error = "Masih kosong"
                }
                negaraAsal.isEmpty() -> {
                    edt_negara_asal.error = "Masih kosong"
                }
                merkBarang.isEmpty() -> {
                    edt_merk_barang.error = "Masih kosong"
                }
                masaGaransi.isEmpty() -> {
                    edt_masa_garansi.error = "Masih kosong"
                }
                beratBarang.isEmpty() -> {
                    edt_berat_barang.error = "Masih kosong"
                }
                stokBarang.isEmpty() -> {
                    edt_stok_barang.error = "Masih kosong"
                }
                fotoBarang.isEmpty() -> {
                    edt_foto_barang.error = "Masih kosong"
                }
                deskripsiBarang.isEmpty() -> {
                    edt_deskripsi_barang.error = "Masih kosong"
                }
                else -> {
                    RetrofitClient.instance.postBarang(namaBarang, hargaBarang.toInt(), negaraAsal,
                        merkBarang, masaGaransi.toInt(), beratBarang.toInt(), stokBarang.toInt(), fotoBarang, deskripsiBarang)
                        .enqueue(object :
                            Callback<DataBarangResponse.BarangResponse> {
                        override fun onFailure(call: Call<DataBarangResponse.BarangResponse>, t: Throwable) {
                            Toast.makeText(this@AddBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataBarangResponse.BarangResponse>,
                            response: Response<DataBarangResponse.BarangResponse>
                        ) {
                            edt_nama_barang.text.clear()
                            edt_harga_barang.text.clear()
                            edt_negara_asal.text.clear()
                            edt_merk_barang.text.clear()
                            edt_masa_garansi.text.clear()
                            edt_berat_barang.text.clear()
                            edt_stok_barang.text.clear()
                            edt_foto_barang.text.clear()
                            edt_deskripsi_barang.text.clear()

                            Toast.makeText(this@AddBarangActivity, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cover, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cari -> {
                val webIntent: Intent = Uri.parse("https://www.google.com").let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                startActivity(webIntent)
                true
            }
            R.id.upload -> {
                val intent = Intent(this, WebViewActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
}