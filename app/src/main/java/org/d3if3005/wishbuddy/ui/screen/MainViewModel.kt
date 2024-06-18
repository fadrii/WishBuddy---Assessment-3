package org.d3if3005.wishbuddy.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3005.wishbuddy.network.ApiStatus
import org.d3if3005.wishbuddy.network.Api
import org.d3if3005.wishbuddy.model.Wishlist
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Wishlist>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var errorMessageNoToast = mutableStateOf<String?>(null)
        private set

    var querySuccess = mutableStateOf(false)
        private set

    var isUploading = mutableStateOf(false)
        private set

    var updateSuccess = mutableStateOf(false)
        private set


    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = Api.service.getAllData(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainVM", "data error: ${e.message}")
                errorMessageNoToast.value = when (e.message) {
                    "HTTP 404 " -> "Anda belum memasukkan data."
                    else -> "Gagal memuat data."
                }
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(
        userEmail: String,
        namaBarang: String,
        harga: String,
        bitmap: Bitmap
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val namaBarangPart = namaBarang.toRequestBody("text/plain".toMediaTypeOrNull())
                val hargaPart = harga.toRequestBody("text/plain".toMediaTypeOrNull())
                val userEmailPart = userEmail.toRequestBody("text/plain".toMediaTypeOrNull())
                isUploading.value = true
                val result =
                    Api.service.addData(
                        namaBarangPart,
                        hargaPart,
                        userEmailPart,
                        bitmap.toMultipartBody()
                    )
                isUploading.value = false
                Log.d("MainVM", "result: $result")
                querySuccess.value = true
                retrieveData(userEmail)
            } catch (e: Exception) {
                Log.d("MainVM", "save error: ${e.message}")
                errorMessage.value = "Terjadi kesalahan, harap coba lagi"
            }
        }
    }

    fun deleteData(email: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Api.service.deleteData(id, email)
                querySuccess.value = true
                retrieveData(email)
            } catch (e: Exception) {
                Log.d("MainVM", "delete error: ${e.message}")
                errorMessage.value = "Terjadi kesalahan, harap coba lagi"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 30, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size
        )
        return MultipartBody.Part.createFormData("file", "image.jpg", requestBody)
    }

    fun clearMessage() {
        errorMessage.value = null
        querySuccess.value = false
        isUploading.value = false
    }

}