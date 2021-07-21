package com.example.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPembayaranResponse(
    val data: ArrayList<PembayaranResponse>?
) : Parcelable {
    @Parcelize
    data class PembayaranResponse(
        @SerializedName("id_pembayaran")
        val idPembayaran: Int?,
        @SerializedName("id_pesanan")
        val idPesanan: Int?,
        @SerializedName("id_penjual")
        val idPenjual: Int?,
        @SerializedName("id_pembeli")
        val idPembeli: Int?,
        @SerializedName("id_barang")
        val idBarang: Int?,
        @SerializedName("nama_barang")
        val namaBarang: String?,
        @SerializedName("tgl_beli")
        val tglBeli: String?,
        @SerializedName("jumlah_beli")
        val jumlahBeli: Int?,
        @SerializedName("harga_barang")
        val hargaBarang: Int?,
        @SerializedName("total_harga")
        val totalHarga: Int?,
        @SerializedName("pembayaran")
        val pembayaran: Int?,
        @SerializedName("kembalian")
        val kembalian: Int?
    ) :Parcelable
}