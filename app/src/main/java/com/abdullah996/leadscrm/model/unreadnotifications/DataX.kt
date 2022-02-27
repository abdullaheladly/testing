package com.abdullah996.leadscrm.model.unreadnotifications


import com.google.gson.annotations.SerializedName

data class UnReadNotifications(
    @SerializedName("id")
    val id: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("msg")
    val msg: String
)