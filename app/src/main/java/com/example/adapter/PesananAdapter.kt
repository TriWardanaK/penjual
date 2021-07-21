package com.example.adapter

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataLoginResponse
import com.example.response.DataPesananResponse
import kotlinx.android.synthetic.main.item_pesanan.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PesananAdapter(private val listBarang: ArrayList<DataPesananResponse.PesananResponse>)
    : RecyclerView.Adapter<PesananAdapter.ViewHolder>(){

    companion object{
        const val EXTRA_PENJUAL_ID = "extra_penjual_id"
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    private var onItemClickCallback2: OnItemClickCallback2? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnItemClickCallback2(onItemClickCallback2: OnItemClickCallback2){
        this.onItemClickCallback2 = onItemClickCallback2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(PesananResponse: DataPesananResponse.PesananResponse){

            fun getIds(): Int?{
                return PesananResponse.idPesanan
            }

            fun getId(): Int? {
                val activity = itemView.context as Activity
                val detailPenjualId = activity.intent.getParcelableExtra(EXTRA_PENJUAL_ID) as DataLoginResponse?
                return detailPenjualId?.idPenjual
            }

            with(itemView){
                Glide.with(itemView.context)
                    .load(PesananResponse.fotoBarang)
                    .into(photo_barang)

                tv_nama_barang.text = PesananResponse.namaBarang
                tv_deskripsi_barang.text = PesananResponse.deskripsiBarang

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(PesananResponse)
                }

                btn_set_bayar.setOnClickListener {
                    onItemClickCallback2?.onItemClicked2(PesananResponse)
                }

                btn_set_hapus.setOnClickListener {

                    Log.d("cek id penjual", "${getId()}")

                    RetrofitClient.instance.deletePesanan(getId(), getIds()).enqueue(object :
                        Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataPesananResponse.PesananResponse)
    }

    interface OnItemClickCallback2{
        fun onItemClicked2(data: DataPesananResponse.PesananResponse)
    }
}