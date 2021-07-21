package com.example.penjual.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataBarangResponse
import com.example.response.DetailDataBarangResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_appbar.*
import kotlinx.android.synthetic.main.detail_deskripsi.*
import kotlinx.android.synthetic.main.detail_info_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_BARANG_ID = "extra_barang_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_backdrop_title.setExpandedTitleColor(Color.TRANSPARENT)

        showDetailBarang()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getId(): Int? {
        val detailBarangId = intent.getParcelableExtra(EXTRA_BARANG_ID) as DataBarangResponse.BarangResponse?
        return detailBarangId?.idBarang
    }

    private fun showDetailBarang() {
        RetrofitClient.instance.getDetailBarang(getId()).enqueue(object :
            Callback<DetailDataBarangResponse> {
            override fun onFailure(call: Call<DetailDataBarangResponse>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailDataBarangResponse>,
                response: Response<DetailDataBarangResponse>
            ) {
                val detail = response.body()

                collapsing_backdrop_title.title = detail?.namaBarang
                tv_nama_barang.text = detail?.namaBarang
                tv_value_harga_barang.text = detail?.hargaBarang.toString()
                tv_value_negara_asal.text = detail?.negaraAsal
                tv_value_merk_barang.text = detail?.merkBarang
                tv_value_masa_garansi.text = detail?.masaGaransi.toString()
                tv_value_berat_barang.text = detail?.beratBarang.toString()
                tv_value_stok_barang.text = detail?.stokBarang.toString()
                tv_value_deskripsi_barang.text = detail?.deskripsiBarang

                Picasso.get()
                    .load(detail?.fotoBarang)
                    .into(iv_backdrop_detail)

                Picasso.get()
                    .load(detail?.fotoBarang)
                    .into(iv_cover_detail)
            }
        })
    }
}