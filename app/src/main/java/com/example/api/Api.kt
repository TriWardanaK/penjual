package com.example.api

import com.example.response.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    //barang

    @GET("/barang")
    fun getBarang(): Call<DataBarangResponse>

    @GET("/barang/{id_barang}")
    fun getDetailBarang(@Path("id_barang") id: Int?): Call<DetailDataBarangResponse>

    @DELETE("/barang/{id_barang}")
    fun deleteBarang(@Path("id_barang") id: Int?): Call<Void>

    @FormUrlEncoded
    @POST("/barang/")
    fun postBarang(@Field("nama_barang") namaBarang: String?,
                 @Field("harga_barang") hargaBarang: Int?,
                 @Field("negara_asal") negaraAsal: String?,
                 @Field("merk_barang") merkBarang: String?,
                 @Field("masa_garansi") masaGaransi: Int?,
                 @Field("berat_barang") beratBarang: Int?,
                 @Field("stok_barang") stokBarang: Int?,
                 @Field("foto_barang") fotoBarang: String?,
                   @Field("deskripsi_barang") deskrpsiBarang: String?
    ): Call<DataBarangResponse.BarangResponse>

    @FormUrlEncoded
    @PUT("/barang/{id_barang}")
    fun putBarang(
            @Path("id_barang") id: String?,
            @Field("nama_barang") namaBarang: String?,
            @Field("harga_barang") hargaBarang: Int?,
            @Field("negara_asal") negaraAsal: String?,
            @Field("merk_barang") merkBarang: String?,
            @Field("masa_garansi") masaGaransi: Int?,
            @Field("berat_barang") beratBarang: Int?,
            @Field("stok_barang") stokBarang: Int?,
            @Field("foto_barang") fotoBarang: String?,
            @Field("deskripsi_barang") deskrpsiBarang: String?
    ): Call<DataBarangResponse.BarangResponse>


    //pesanan

    @GET("/penjual/{id_penjual}/pesanan")
    fun getPesanan(@Path("id_penjual") id: Int?): Call<DataPesananResponse>

    @DELETE("/penjual/{id_penjual}/pesanan/{id_pesanan}")
    fun deletePesanan(
        @Path("id_penjual") idPenjual: Int?,
        @Path("id_pesanan") idPesanan: Int?,
    ): Call<Void>

    @GET("/penjual/{id_penjual}/pesanan/{id_pesanan2}")
    fun getDetailPesanan(
        @Path("id_penjual") id: Int?,
        @Path("id_pesanan2") idPesanan: Int?,
    ): Call<DetailDataPesananResponse>

    //pembayaran

    @FormUrlEncoded
    @POST("/penjual/{id_penjual}/pembayaran")
    fun postPembayaran(
        @Path("id_penjual") id: Int?,
        @Field("id_pesanan") idPesanan: Int?,
        @Field("id_penjual") idPenjual: Int?,
        @Field("id_pembeli") idPembeli: Int?,
        @Field("id_barang") idBarang: Int?,
        @Field("nama_barang") namaBarang: String?,
        @Field("tgl_beli") tglBeli: String?,
        @Field("jumlah_beli") jumlahBeli: Int?,
        @Field("harga_barang") hargaBarang: Int?,
        @Field("total_harga") totalHarga: String?,
        @Field("pembayaran") pembayaran: String?,
        @Field("kembalian") kembalian: String?,
    ): Call<DataPembayaranResponse.PembayaranResponse>


    //penjual

    @GET("/penjual/{id_penjual}")
    fun getDetailPenjual(@Path("id_penjual") id: Int?): Call<DetailPenjualResponse>

    @FormUrlEncoded
    @POST("/penjual/")
    fun postPenjual(@Field("username_penjual") username: String?,
                    @Field("password_penjual") password: String?,
                    @Field("nama_penjual") nama: String?,
                    @Field("no_telp_penjual") telp: String?,
                    @Field("alamat_penjual") alamat: String?,
    ): Call<DataPenjualResponse.PenjualResponse>

    @FormUrlEncoded
    @POST("/login/")
    fun postLoginPenjual(@Field("username_penjual") username: String?,
                       @Field("password_penjual") password: String?,
    ): Call<DataLoginResponse>

    @FormUrlEncoded
    @PUT("/penjual/{id_penjual}")
    fun putPenjual(
        @Path("id_penjual") id: String?,
        @Field("username_penjual") username: String?,
        @Field("password_penjual") password: String?,
        @Field("nama_penjual") nama: String?,
        @Field("no_telp_penjual") telp: String?,
        @Field("alamat_penjual") alamat: String?,
    ): Call<DataPenjualResponse.PenjualResponse>

    @DELETE("/penjual/{id_penjual}")
    fun deletePenjual(@Path("id_penjual") id: Int?): Call<Void>
}