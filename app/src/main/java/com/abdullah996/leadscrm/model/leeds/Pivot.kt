package com.abdullah996.leadscrm.model.leeds


import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("lead_id")
    val leadId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)