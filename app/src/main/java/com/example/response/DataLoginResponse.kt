package com.example.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataLoginResponse(
    @SerializedName("id_penjual")
    val idPenjual: Int?,
    @SerializedName("username_penjual")
    val usernamePenjual: String?,
    @SerializedName("password_penjual")
    val passwordPenjual: String?,
) : Parcelable