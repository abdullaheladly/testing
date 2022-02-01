package com.abdullah996.leadscrm.model.getstatus


import com.google.gson.annotations.SerializedName

data class Datad(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("pivot")
    val pivot: Pivotd,
    @SerializedName("updated_at")
    val updatedAt: String
)