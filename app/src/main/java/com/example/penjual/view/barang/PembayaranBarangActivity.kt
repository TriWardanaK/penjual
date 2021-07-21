package com.example.penjual.view.barang

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataLoginResponse
import com.example.response.DataPembayaranResponse
import com.example.response.DataPesananResponse
import com.example.response.DetailDataPesananResponse
import kotlinx.android.synthetic.main.activity_pembayaran_barang.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PembayaranBarangActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PENJUAL_ID = "extra_penjual_id"
        const val EXTRA_PESANAN_ID = "extra_pesanan_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran_barang)

        btn_save.isEnabled = false

        showDetailPesanan()
    }

    private fun getId(): Int? {
        val detailPenjualId = intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?
        return detailPenjualId?.idPenjual
    }

    private fun getIds(): Int? {
        val detailPesananId = intent.getParcelableExtra(EXTRA_PESANAN_ID) as DataPesananResponse.PesananResponse?
        return detailPesananId?.idPesanan
    }

    private fun showDetailPesanan(){
        RetrofitClient.instance.getDetailPesanan(getId(), getIds()).enqueue(object :
            Callback<DetailDataPesananResponse> {
            override fun onFailure(call: Call<DetailDataPesananResponse>, t: Throwable) {
                Toast.makeText(this@PembayaranBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailDataPesananResponse>,
                response: Response<DetailDataPesananResponse>
            ) {
                val detail = response.body()

                tv_id_pesanan.text = detail?.idPesanan.toString()
                tv_id_penjual.text = getId().toString()
                tv_id_pembeli.text = detail?.idPembeli.toString()
                tv_id_barang.text = detail?.idBarang.toString()
                tv_nama_barang.text = detail?.namaBarang
                tv_jumlah_beli.text = detail?.jumlahBeli.toString()
                tv_harga_barang.text = detail?.hargaBarang.toString()
                tv_tgl_beli.text = tglBeli()

                val jumlahBeli = tv_jumlah_beli.text.toString()
                val hargaBarang = tv_harga_barang.text.toString()

                tv_total_harga.text = calculateTotalHarga(jumlahBeli, hargaBarang).toString()

                btn_pembayaran.setOnClickListener {
                    val default = 0
                    if (edt_pembayaran.text.toString().isEmpty()){
                        edt_pembayaran.error = "Masih Kosong"
                    }else{
                        val pembayaran = edt_pembayaran.text.toString()
                        val totalHarga = tv_total_harga.text.toString()

                        if (calculatePembayaran(pembayaran, totalHarga) < default){
                            Toast.makeText(this@PembayaranBarangActivity, "Pembayaran Kurang", Toast.LENGTH_SHORT).show()
                            tv_kembalian.text = default.toString()
                            btn_save.isEnabled = false
                        }else{
                            tv_kembalian.text = calculatePembayaran(pembayaran, totalHarga).toString()
                            btn_save.isEnabled = true
                        }
                    }
                }

                btn_save.setOnClickListener {
                    val pembayaran = edt_pembayaran.text.toString()
                    when {
                        pembayaran.isEmpty() -> {
                            edt_pembayaran.error = "Masih kosong"
                        }
                        else -> {
                            RetrofitClient.instance.postPembayaran(getId(), getIds(), getId(), detail?.idPembeli,
                                detail?.idBarang, detail?.namaBarang, tv_tgl_beli.text.toString(), detail?.jumlahBeli,
                                detail?.hargaBarang, tv_total_harga.text.toString(), edt_pembayaran.text.toString(),
                                tv_kembalian.text.toString()).enqueue(object :
                                Callback<DataPembayaranResponse.PembayaranResponse> {
                                override fun onFailure(call: Call<DataPembayaranResponse.PembayaranResponse>, t: Throwable) {
                                    Toast.makeText(this@PembayaranBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                                }

                                override fun onResponse(
                                    call: Call<DataPembayaranResponse.PembayaranResponse>,
                                    response: Response<DataPembayaranResponse.PembayaranResponse>
                                ) {
                                    GlobalScope.launch {
                                        delay(2000)
                                        val intent = Intent(this@PembayaranBarangActivity, PesananBarangActivity::class.java)
                                        intent.putExtra(PesananBarangActivity.EXTRA_PENJUAL_ID, response.body())
                                        startActivity(intent)
                                        finish()
                                    }

                                    Toast.makeText(this@PembayaranBarangActivity, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                }
            }
        })
    }

    fun calculateTotalHarga(jumlahBeli: String, hargaBarang: String): Int{
        val result = jumlahBeli.toInt() * hargaBarang.toInt()
        return result
    }

    fun calculatePembayaran(pembayaran: String, totalHarga: String): Int{
        val result = pembayaran.toInt() - totalHarga.toInt()
        return result
    }

    @SuppressLint("SimpleDateFormat")
    private fun tglBeli() : String{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        if (month == 12) {
            val df: DateFormat = SimpleDateFormat("${year + 1}-${month - 11}-$day")
            return df.format(calendar.time)
        }
        else if (month < 12) {
            val df: DateFormat = SimpleDateFormat("$year-${month + 1}-$day")
            return df.format(calendar.time)
        }
        return String()
    }
}