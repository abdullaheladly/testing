package com.abdullah996.leadscrm.model.user


import com.google.gson.annotations.SerializedName

data class company(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)