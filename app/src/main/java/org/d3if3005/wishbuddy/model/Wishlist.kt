package org.d3if3005.wishbuddy.model

data class Wishlist(
    val id: Int,
    val user_email: String,
    val nama_barang: String,
    val harga: Int,
    val is_done: Boolean,
    val image_id: String,
    val created_at: String
)