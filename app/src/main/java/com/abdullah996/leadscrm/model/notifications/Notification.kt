package com.abdullah996.leadscrm.model.notifications


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("data")
    val `data`: NotificationData,
    @SerializedName("id")
    val id: String,
    @SerializedName("notifiable_id")
    val notifiableId: Int,
    @SerializedName("read_at")
    val readAt: Any?
)