package com.abdullah996.leadscrm.model.unreadnotifications


import com.google.gson.annotations.SerializedName

data class UnReadNotificationsData(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("data")
    val `data`: UnReadNotifications,
    @SerializedName("id")
    val id: String,
    @SerializedName("notifiable_id")
    val notifiableId: Int,
    @SerializedName("notifiable_type")
    val notifiableType: String,
    @SerializedName("read_at")
    val readAt: Any,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)