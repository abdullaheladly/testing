package com.abdullah996.leadscrm.model.statusmodel


import com.google.gson.annotations.SerializedName

data class StatusModelResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)