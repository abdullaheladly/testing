package com.abdullah996.leadscrm.model.leeds


import com.google.gson.annotations.SerializedName

data class LeedsReponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)