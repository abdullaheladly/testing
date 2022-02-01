package com.abdullah996.leadscrm.model.delete

import com.google.gson.annotations.SerializedName

data class DeleteLeadResponse(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)
