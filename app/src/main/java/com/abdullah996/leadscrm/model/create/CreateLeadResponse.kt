package com.abdullah996.leadscrm.model.create


import com.google.gson.annotations.SerializedName

data class CreateLeadResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)