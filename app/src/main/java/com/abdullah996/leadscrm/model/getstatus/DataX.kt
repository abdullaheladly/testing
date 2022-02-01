package com.abdullah996.leadscrm.model.getstatus


import com.google.gson.annotations.SerializedName

data class Actions(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)