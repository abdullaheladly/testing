package com.abdullah996.leadscrm.model.baseactions


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)