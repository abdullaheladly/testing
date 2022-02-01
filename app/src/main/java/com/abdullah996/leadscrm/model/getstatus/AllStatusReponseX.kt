package com.abdullah996.leadscrm.model.getstatus


import com.google.gson.annotations.SerializedName

data class AllStatusReponse(
    @SerializedName("data")
    val `data`: List<Actions>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)