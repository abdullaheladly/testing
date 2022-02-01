package com.abdullah996.leadscrm.model.getstatus


import com.google.gson.annotations.SerializedName

data class Pivotd(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("lead_id")
    val leadId: Int,
    @SerializedName("lead_status_id")
    val leadStatusId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)