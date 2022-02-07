package com.abdullah996.leadscrm.model.notifications


import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("msg")
    val msg: String
)