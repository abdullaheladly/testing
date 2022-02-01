package com.abdullah996.leadscrm.model.create


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("phones")
    val phones: List<String>,
    @SerializedName("updated_at")
    val updatedAt: String
)