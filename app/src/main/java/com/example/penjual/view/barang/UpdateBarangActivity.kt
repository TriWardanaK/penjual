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
import com.example.response.DetailDataBarangResponse
import kotlinx.android.synthetic.main.activity_update_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateBarangActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_BARANG_ID = "extra_barang_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_barang)

        showDetailBarang()

        btn_update.setOnClickListener {
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
                    RetrofitClient.instance.putBarang(getId().toString(), namaBarang, hargaBarang.toInt(), negaraAsal,
                        merkBarang, masaGaransi.toInt(), beratBarang.toInt(), stokBarang.toInt(), fotoBarang, deskripsiBarang)
                        .enqueue(object :
                            Callback<DataBarangResponse.BarangResponse> {
                        override fun onFailure(call: Call<DataBarangResponse.BarangResponse>, t: Throwable) {
                            Toast.makeText(this@UpdateBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataBarangResponse.BarangResponse>,
                            response: Response<DataBarangResponse.BarangResponse>
                        ) {
                            Toast.makeText(this@UpdateBarangActivity, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    private fun getId(): Int? {
        val detailBarangId = intent.getParcelableExtra(EXTRA_BARANG_ID) as DataBarangResponse.BarangResponse?
        return detailBarangId?.idBarang
    }

    private fun showDetailBarang() {
        RetrofitClient.instance.getDetailBarang(getId()).enqueue(object :
            Callback<DetailDataBarangResponse> {
            override fun onFailure(call: Call<DetailDataBarangResponse>, t: Throwable) {
                Toast.makeText(this@UpdateBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailDataBarangResponse>,
                response: Response<DetailDataBarangResponse>
            ) {
                val detail = response.body()
                edt_nama_barang.setText(detail?.namaBarang)
                edt_harga_barang.setText(detail?.hargaBarang.toString())
                edt_negara_asal.setText(detail?.negaraAsal)
                edt_merk_barang.setText(detail?.merkBarang)
                edt_masa_garansi.setText(detail?.masaGaransi.toString())
                edt_berat_barang.setText(detail?.beratBarang.toString())
                edt_stok_barang.setText(detail?.stokBarang.toString())
                edt_foto_barang.setText(detail?.fotoBarang)
                edt_deskripsi_barang.setText(detail?.deskripsiBarang)
            }
        })
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