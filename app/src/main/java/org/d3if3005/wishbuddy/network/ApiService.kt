package org.d3if3005.wishbuddy.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3005.wishbuddy.model.MessageResponse
import org.d3if3005.wishbuddy.model.Wishlist
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://wish-buddy.vercel.app/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface UserApi {
    @Multipart
    @POST("wishlists/")
    suspend fun addData(
        @Part("nama_barang") namaBarang: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("user_email") userEmail: RequestBody,
        @Part file: MultipartBody.Part
    ): Wishlist
    @GET("wishlists/")
    suspend fun getAllData(
        @Query("email") email: String,
    ): List<Wishlist>

    @PUT("wishlists/{wishlist_id}")
    suspend fun isDone(
        @Path("wishlist_id") isDone: Int
    ): MessageResponse

    @DELETE("wishlists/{wishlist_id}")
    suspend fun deleteData(
        @Path("wishlist_id") id: Int,
        @Query("email") email: String
    ): MessageResponse
}


object Api {
    val service: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    fun getImageUrl(imageId: String): String{
        return BASE_URL + "wishlists/images/$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }