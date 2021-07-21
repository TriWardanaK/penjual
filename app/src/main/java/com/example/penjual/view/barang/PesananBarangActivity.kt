package com.example.penjual.view.barang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.PesananAdapter
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataLoginResponse
import com.example.response.DataPesananResponse
import kotlinx.android.synthetic.main.activity_pesanan_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananBarangActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PENJUAL_ID = "extra_penjual_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_barang)

        showPesanan()

        refreshLayout.setOnRefreshListener {
            showPesanan()
            refreshLayout.isRefreshing = false
        }

        Log.d("cek id penjual", "${getId()}")
    }

   private fun getId(): Int? {
        val detailBarangId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?
        return detailBarangId?.idPenjual
    }

    private fun showPesanan() {
        rv_pesanan.setHasFixedSize(true)
        rv_pesanan.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getPesanan(getId()).enqueue(object :
            Callback<DataPesananResponse> {
            override fun onFailure(call: Call<DataPesananResponse>, t: Throwable) {
                Toast.makeText(this@PesananBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataPesananResponse>,
                response: Response<DataPesananResponse>
            ) {
                val list = response.body()?.data
                val pesananAdapter = list?.let { PesananAdapter(it) }
                rv_pesanan.adapter = pesananAdapter

                pesananAdapter?.setOnItemClickCallback(object : PesananAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DataPesananResponse.PesananResponse) {
//                        showSelectedFavorite(data)
                    }
                })

                pesananAdapter?.setOnItemClickCallback2(object : PesananAdapter.OnItemClickCallback2 {
                    override fun onItemClicked2(data: DataPesananResponse.PesananResponse) {
                        showSelectedBayar(data)
                    }
                })
            }
        })
    }

    private fun showSelectedBayar(PesananResponse: DataPesananResponse.PesananResponse){
        val penjualId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?

        val moveObject = Intent(this@PesananBarangActivity, PembayaranBarangActivity::class.java)
        moveObject.putExtra(PembayaranBarangActivity.EXTRA_PENJUAL_ID, penjualId)
        moveObject.putExtra(PembayaranBarangActivity.EXTRA_PESANAN_ID, PesananResponse)
        startActivity(moveObject)
    }
}