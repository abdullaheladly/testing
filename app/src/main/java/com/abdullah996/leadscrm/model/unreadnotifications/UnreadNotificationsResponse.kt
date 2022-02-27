package com.abdullah996.leadscrm.model.unreadnotifications


import com.google.gson.annotations.SerializedName

data class UnreadNotificationsResponse(
    @SerializedName("data")
    val `data`: List<UnReadNotificationsData>?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)