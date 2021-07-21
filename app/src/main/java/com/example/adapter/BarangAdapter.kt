package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.api.RetrofitClient
import com.example.penjual.R
import com.example.response.DataBarangResponse
import kotlinx.android.synthetic.main.item_barang.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarangAdapter(private val listBarang: ArrayList<DataBarangResponse.BarangResponse>)
    : RecyclerView.Adapter<BarangAdapter.ViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    private var onItemClickCallback2: OnItemClickCallback2? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnItemClickCallback2(onItemClickCallback2: OnItemClickCallback2){
        this.onItemClickCallback2 = onItemClickCallback2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(BarangResponse: DataBarangResponse.BarangResponse){

            fun getIds(): Int? {
                return BarangResponse.idBarang
            }

            with(itemView){
                Glide.with(itemView.context)
                        .load(BarangResponse.fotoBarang)
                        .into(photo_barang)

                tv_nama_barang.text = BarangResponse.namaBarang
                tv_deskripsi_barang.text = BarangResponse.deskripsiBarang

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(BarangResponse)
                }

                btn_set_ubah.setOnClickListener{
                    onItemClickCallback2?.onItemClicked2(BarangResponse)
                }

                btn_set_hapus.setOnClickListener{
                    RetrofitClient.instance.deleteBarang(getIds()).enqueue(object :
                            Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                                call: Call<Void>,
                                response: Response<Void>
                        ) {
                            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataBarangResponse.BarangResponse)
    }

    interface OnItemClickCallback2{
        fun onItemClicked2(data: DataBarangResponse.BarangResponse)
    }

}
