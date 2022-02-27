package com.abdullah996.leadscrm.model.statusmodel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("leads_count")
    val leadsCount: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: Any
)