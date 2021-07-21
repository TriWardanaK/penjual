package com.example.penjual.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.BarangAdapter
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.penjual.view.barang.AddBarangActivity
import com.example.penjual.view.barang.PesananBarangActivity
import com.example.penjual.view.barang.UpdateBarangActivity
import com.example.penjual.view.penjual.LoginActivity
import com.example.penjual.view.penjual.UpdateActivity
import com.example.response.DataBarangResponse
import com.example.response.DataLoginResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PENJUAL_ID = "extra_penjual_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showBarang()

        refreshLayout.setOnRefreshListener {
            showBarang()
            refreshLayout.isRefreshing = false
        }

        Log.d("cek id penjual", "${getId()}")
    }

    private fun getId(): Int? {
        val detailPenjualId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?
        return detailPenjualId?.idPenjual
    }

    private fun showBarang() {
        rv_barang.setHasFixedSize(true)
        rv_barang.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getBarang().enqueue(object :
                Callback<DataBarangResponse> {
            override fun onFailure(call: Call<DataBarangResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                    call: Call<DataBarangResponse>,
                    response: Response<DataBarangResponse>
            ) {
                val list = response.body()?.data
                val barangAdapter = list?.let { BarangAdapter(it) }
                rv_barang.adapter = barangAdapter

                barangAdapter?.setOnItemClickCallback(object : BarangAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DataBarangResponse.BarangResponse) {
                        showSelectedBarang(data)
                    }
                })

                barangAdapter?.setOnItemClickCallback2(object : BarangAdapter.OnItemClickCallback2 {
                    override fun onItemClicked2(data: DataBarangResponse.BarangResponse) {
                        showSelectedUpdate(data)
                    }
                })
            }
        })
    }

    private fun showSelectedBarang(BarangResponse: DataBarangResponse.BarangResponse){
        val moveObject = Intent(this@MainActivity, DetailActivity::class.java)
        moveObject.putExtra(DetailActivity.EXTRA_BARANG_ID, BarangResponse)
        startActivity(moveObject)
    }

    private fun showSelectedUpdate(BarangResponse: DataBarangResponse.BarangResponse){
        val moveObject = Intent(this@MainActivity, UpdateBarangActivity::class.java)
        moveObject.putExtra(UpdateBarangActivity.EXTRA_BARANG_ID, BarangResponse)
        startActivity(moveObject)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pesanan -> {
                val penjualId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?

                val intent = Intent(this, PesananBarangActivity::class.java)
                intent.putExtra(PesananBarangActivity.EXTRA_PENJUAL_ID, penjualId)
                startActivity(intent)
                true
            }
            R.id.add_buku -> {
                val intent = Intent(this, AddBarangActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting -> {
                val adminId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?

                val intent = Intent(this, UpdateActivity::class.java)
                intent.putExtra(UpdateActivity.EXTRA_PENJUAL_ID, adminId)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                finishAffinity()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
}