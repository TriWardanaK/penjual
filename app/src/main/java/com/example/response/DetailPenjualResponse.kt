package com.example.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPenjualResponse (
    @SerializedName("id_penjual")
    val idPenjual: Int?,
    @SerializedName("username_penjual")
    val usernamePenjual: String?,
    @SerializedName("password_penjual")
    val passwordPenjual: String?,
    @SerializedName("nama_penjual")
    val namaPenjual: String?,
    @SerializedName("no_telp_penjual")
    val telpPenjual: String?,
    @SerializedName("alamat_penjual")
    val alamatPenjual: String?
): Parcelable