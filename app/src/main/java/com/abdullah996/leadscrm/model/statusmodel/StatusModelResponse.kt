package com.abdullah996.leadscrm.model.statusmodel


import com.google.gson.annotations.SerializedName

data class StatusModelResponse(
    @SerializedName("data")
    val `data`: List<StatusModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)