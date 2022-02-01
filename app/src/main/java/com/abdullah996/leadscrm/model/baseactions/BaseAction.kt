package com.abdullah996.leadscrm.model.baseactions


import com.google.gson.annotations.SerializedName

data class BaseAction(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)