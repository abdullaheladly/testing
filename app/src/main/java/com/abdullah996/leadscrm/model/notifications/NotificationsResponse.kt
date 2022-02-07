package com.abdullah996.leadscrm.model.notifications


import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)