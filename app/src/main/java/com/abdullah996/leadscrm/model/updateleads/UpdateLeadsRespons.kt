package com.abdullah996.leadscrm.model.updateleads


import com.google.gson.annotations.SerializedName

data class UpdateLeadsRespons(
    @SerializedName("data")
    val `data`: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)